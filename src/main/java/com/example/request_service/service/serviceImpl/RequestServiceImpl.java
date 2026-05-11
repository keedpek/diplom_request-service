package com.example.request_service.service.serviceImpl;

import com.example.request_service.messaging.event.EventDto;
import com.example.request_service.messaging.event.payload.ExecutorFoundEventDto;
import com.example.request_service.messaging.event.payload.RequestAssignedEventDto;
import com.example.request_service.messaging.event.payload.RequestCreatedEventDto;
import com.example.request_service.DTO.request.*;
import com.example.request_service.entity.Category;
import com.example.request_service.entity.Request;
import com.example.request_service.enums.EventType;
import com.example.request_service.enums.RequestPriority;
import com.example.request_service.enums.RequestStatus;
import com.example.request_service.exceptions.NotFoundException;
import com.example.request_service.mapper.RequestMapper;
import com.example.request_service.messaging.event.payload.RequestStatusChangedEventDto;
import com.example.request_service.repository.CategoryRepository;
import com.example.request_service.repository.RequestRepository;
import com.example.request_service.repository.specifications.RequestSpecifications;
import com.example.request_service.service.KafkaOutboxService;
import com.example.request_service.service.RequestService;
import com.example.request_service.service.SlaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

  private final RequestRepository requestRepository;
  private final SlaService slaService;
  private final RequestMapper requestMapper;
  private final CategoryRepository categoryRepository;
  private final KafkaOutboxService kafkaOutboxService;


  @Override
  @Transactional
  public RequestResponseDto create(CreateRequestDto createRequestDto) {
    log.info(
            "Создание заявки: category={}, priority={}",
            createRequestDto.getCategoryCode(),
            createRequestDto.getPriority()
    );

    Request request = requestMapper.toEntity(createRequestDto);

    Category category = categoryRepository.findByCode(createRequestDto.getCategoryCode())
            .orElseThrow(() -> {
              log.warn("Категория не найдена: code={}", createRequestDto.getCategoryCode());
              return new NotFoundException("Категория не найдена");
            });

    request.setCategory(category);
    request.setCreatedAt(LocalDateTime.now());
    request.setUpdatedAt(LocalDateTime.now());

    request.setDeadline(slaService.calculateDeadline(
            category,
            RequestPriority.valueOf(createRequestDto.getPriority())
    ));
    log.debug("Рассчитан дедлайн: {}", request.getDeadline());

    Request savedRequest = requestRepository.save(request);
    log.info("Заявка создана: id={}", savedRequest.getId());

    EventDto<RequestCreatedEventDto> event = EventDto.<RequestCreatedEventDto>builder()
            .eventId(UUID.randomUUID())
            .eventType(EventType.REQUEST_CREATED)
            .timestamp(LocalDateTime.now())
            .payload(RequestCreatedEventDto.builder()
                    .requestId(savedRequest.getId())
                    .createdByUserId(savedRequest.getCreatedByUserId())
                    .title(savedRequest.getTitle())
                    .build())
            .build();
    kafkaOutboxService.save(event, "request-created", savedRequest.getId().toString());
    log.info("Событие сохранено: id={}", event.getEventId());
    return requestMapper.toDto(savedRequest);
  }

  @Override
  public RequestResponseDto getById(UUID id) {
    log.debug("Получение заявки: id={}", id);
    return requestMapper.toDto(findById(id));
  }

  @Override
  public List<RequestResponseDto> getAll(RequestFilter filter) {
    log.debug(
            "Поиск заявок с фильтром: categoryCode={}, priority={}, status={}, assignedToUserId={}",
            filter.getCategoryCode(),
            filter.getPriority(),
            filter.getStatus(),
            filter.getAssignedToUserId()
    );

    List<Request> requests = requestRepository.findAll(
            RequestSpecifications.withFilter(filter)
    );
    return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public RequestResponseDto updateStatus(UUID id, UpdateRequestStatusDto updateRequestStatusDto) {
    log.info("Обновление статуса заявки: id={}, status={}", id, updateRequestStatusDto.getStatus());
    Request request = findById(id);
    request.setStatus(RequestStatus.valueOf(updateRequestStatusDto.getStatus()));
    request.setUpdatedAt(LocalDateTime.now());
    log.debug("Статус обновлен: id={}", request.getId());

    EventDto<RequestStatusChangedEventDto> event = EventDto.<RequestStatusChangedEventDto>builder()
            .eventId(UUID.randomUUID())
            .eventType(EventType.STATUS_CHANGED)
            .timestamp(LocalDateTime.now())
            .payload(RequestStatusChangedEventDto.builder()
                    .requestId(request.getId())
                    .status(updateRequestStatusDto.getStatus())
                    .userIds(List.of(request.getAssignedToUserId(), request.getCreatedByUserId()))
                    .build())
            .build();
    kafkaOutboxService.save(event, "request-status-changed", request.getId().toString());
    return requestMapper.toDto(request);
  }

  @Override
  @Transactional
  public void assign(ExecutorFoundEventDto event) {
    log.info(
            "Назначение исполнителя: requestId={}, executorId={}",
            event.getRequestId(),
            event.getAssignedUserId()
    );

    Request request = findById(event.getRequestId());
    request.setAssignedToUserId(event.getAssignedUserId());
    request.setStatus(RequestStatus.ASSIGNED);
    request.setUpdatedAt(LocalDateTime.now());
    log.debug("Пользователь назначен: id={}", request.getAssignedToUserId());

    EventDto<RequestAssignedEventDto> assignmentEvent = EventDto.<RequestAssignedEventDto>builder()
            .eventId(UUID.randomUUID())
            .eventType(EventType.REQUEST_ASSIGNED)
            .timestamp(LocalDateTime.now())
            .payload(RequestAssignedEventDto.builder()
                    .requestId(event.getRequestId())
                    .assignedUserId(event.getAssignedUserId())
                    .createdByUserId(request.getCreatedByUserId())
                    .title(request.getTitle())
                    .build())
            .build();
    kafkaOutboxService.save(assignmentEvent, "request-assigned", request.getId().toString());
  }

  private Request findById(UUID id) {
    return requestRepository.findById(id).orElseThrow(() -> new NotFoundException("Запрос не найден"));
  }
}
