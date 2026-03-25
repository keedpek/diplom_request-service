package com.example.request_service.service.serviceImpl;

import com.example.request_service.DTO.request.CreateRequestDto;
import com.example.request_service.DTO.request.RequestFilter;
import com.example.request_service.DTO.request.RequestResponseDto;
import com.example.request_service.entity.Category;
import com.example.request_service.entity.Request;
import com.example.request_service.enums.RequestPriority;
import com.example.request_service.enums.RequestStatus;
import com.example.request_service.exceptions.NotFoundException;
import com.example.request_service.mapper.RequestMapper;
import com.example.request_service.repository.CategoryRepository;
import com.example.request_service.repository.RequestRepository;
import com.example.request_service.repository.specifications.RequestSpecifications;
import com.example.request_service.service.RequestService;
import com.example.request_service.service.SlaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

  private final RequestRepository requestRepository;
  private final SlaService slaService;
  private final RequestMapper requestMapper;
  private final CategoryRepository categoryRepository;


  @Override
  @Transactional
  public RequestResponseDto create(CreateRequestDto createRequestDto) {
    Request request = requestMapper.toEntity(createRequestDto);

    Category category = categoryRepository.findByCode(createRequestDto.getCategoryCode())
            .orElseThrow(() -> new NotFoundException("Категория не найдена"));

    request.setCategory(category);
    request.setCreatedAt(LocalDateTime.now());
    request.setUpdatedAt(LocalDateTime.now());

    request.setDeadline(slaService.calculateDeadline(
            category,
            RequestPriority.valueOf(createRequestDto.getPriority())
    ));
    return requestMapper.toDto(requestRepository.save(request));
  }

  @Override
  public RequestResponseDto getById(UUID id) {
    return requestMapper.toDto(findById(id));
  }

  @Override
  public List<RequestResponseDto> getAll(RequestFilter filter) {
    List<Request> requests = requestRepository.findAll(
            RequestSpecifications.withFilter(filter)
    );
    return requests.stream().map(requestMapper::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public RequestResponseDto updateStatus(UUID id, String status) {
    Request request = findById(id);
    request.setStatus(RequestStatus.valueOf(status));
    request.setUpdatedAt(LocalDateTime.now());
    return requestMapper.toDto(request);
  }

  @Override
  @Transactional
  public void assign(UUID requestId, UUID executorId) {
    Request request = findById(requestId);
    request.setAssignedToUserId(executorId);
    request.setStatus(RequestStatus.ASSIGNED);
    request.setUpdatedAt(LocalDateTime.now());
  }

  private Request findById(UUID id) {
    return requestRepository.findById(id).orElseThrow(() -> new NotFoundException("Запрос не найден"));
  }
}
