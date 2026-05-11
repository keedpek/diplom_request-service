package com.example.request_service.scheduler;

import com.example.request_service.config.OutboxConfig;
import com.example.request_service.entity.KafkaOutboxEvent;
import com.example.request_service.repository.KafkaOutboxRepository;
import com.example.request_service.service.KafkaOutboxEventProcessor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaOutboxScheduler {

  private final KafkaOutboxRepository kafkaOutboxRepository;
  private final KafkaOutboxEventProcessor eventProcessor;
  private final OutboxConfig outboxConfig;

  private ExecutorService executorService;

  @PostConstruct
  public void init() {
    this.executorService = Executors.newFixedThreadPool(outboxConfig.getThreadPoolSize());
  }

  @Scheduled(fixedRateString = "${app.outbox.scheduler-interval-ms}")
  public void processEvents() {
    List<KafkaOutboxEvent> events = kafkaOutboxRepository.lockBatch(outboxConfig.getBatchSize());
    log.info("Обработка {} событий(-ия)", events.size());

    List<CompletableFuture<Void>> futures = events.stream()
            .map(event -> CompletableFuture.runAsync(() -> eventProcessor.process(event), executorService))
            .toList();

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
  }
}
