package com.example.request_service.service;

import com.example.request_service.entity.KafkaOutboxEvent;

public interface KafkaOutboxEventProcessor {
  void process(KafkaOutboxEvent event);
}
