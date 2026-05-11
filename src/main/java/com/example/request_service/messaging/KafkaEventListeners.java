package com.example.request_service.messaging;

import com.example.request_service.messaging.event.EventDto;
import com.example.request_service.messaging.event.payload.ExecutorFoundEventDto;
import com.example.request_service.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventListeners {

  private final RequestService requestService;

  @KafkaListener(topics = "request-executor-found")
  public void onExecutorFound(
          EventDto<ExecutorFoundEventDto> event,
          Acknowledgment acknowledgment
  ) {
    log.info("Получено событие об определении исполнителя: eventId={}", event.getEventId());
    ExecutorFoundEventDto payload = event.getPayload();
    requestService.assign(payload);
    acknowledgment.acknowledge();
  }
}
