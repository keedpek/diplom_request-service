package com.example.request_service.service.serviceImpl;

import com.example.request_service.entity.KafkaOutboxEvent;
import com.example.request_service.enums.KafkaOutboxStatus;
import com.example.request_service.service.KafkaOutboxEventProcessor;
import com.example.request_service.util.OutboxConstants;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaOutboxEventProcessorImpl implements KafkaOutboxEventProcessor {

  private final EntityManager entityManager;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void process(KafkaOutboxEvent event) {
    KafkaOutboxEvent managedEvent = entityManager.merge(event);
    try {
      kafkaTemplate.send(managedEvent.getTopic(), managedEvent.getMessageKey(), managedEvent.getPayload());
      managedEvent.setStatus(KafkaOutboxStatus.SENT);
      log.info("Сообщение отправлено: topic={}, id={}", managedEvent.getTopic(), managedEvent.getId());
    } catch (Exception e) {
      log.error("Ошибка при отправке сообщения в kafka: {}", e.getMessage());
      handleFailure(managedEvent);
    }
  }

  private void handleFailure(KafkaOutboxEvent event) {
    int retries = event.getRetryCount() + 1;

    if (retries > OutboxConstants.MAX_RETRIES) {
      event.setStatus(KafkaOutboxStatus.DEAD);
    } else {
      event.setStatus(KafkaOutboxStatus.NEW);
      event.setNextRetryAt(LocalDateTime.now().plusSeconds((long) Math.pow(2, retries)));
    }

    event.setRetryCount(retries);
  }
}
