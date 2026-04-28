package com.example.request_service.service.serviceImpl;

import com.example.request_service.entity.KafkaOutboxEvent;
import com.example.request_service.enums.KafkaOutboxStatus;
import com.example.request_service.exceptions.OutboxPersistenceException;
import com.example.request_service.messaging.event.EventDto;
import com.example.request_service.repository.KafkaOutboxRepository;
import com.example.request_service.service.KafkaOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaOutboxServiceImpl implements KafkaOutboxService {

  private final KafkaOutboxRepository kafkaOutboxRepository;

  @Override
  public void save(EventDto<?> eventDto, String topic, String key) {
    try {
      KafkaOutboxEvent outboxEvent = KafkaOutboxEvent.builder()
              .id(UUID.randomUUID())
              .topic(topic)
              .messageKey(key)
              .payload(eventDto)
              .status(KafkaOutboxStatus.NEW)
              .retryCount(0)
              .createdAt(LocalDateTime.now())
              .build();
      kafkaOutboxRepository.save(outboxEvent);
    } catch (Exception e) {
      log.error("Ошибка при сохранении события в outbox: eventId={}, message={}", eventDto.getEventId(), e.getMessage());
      throw new OutboxPersistenceException(e.getMessage());
    }
  }
}
