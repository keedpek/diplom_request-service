package com.example.request_service.service;

import com.example.request_service.messaging.event.EventDto;

public interface KafkaOutboxService {
  void save(EventDto<?> eventDto, String topic, String key);
}
