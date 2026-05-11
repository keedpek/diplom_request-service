package com.example.request_service.entity;

import com.example.request_service.enums.KafkaOutboxStatus;
import com.example.request_service.messaging.event.EventDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kafka_outbox")
public class KafkaOutboxEvent {

  @Id
  private UUID id;

  @Column(nullable = false)
  private String topic;

  private String messageKey;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb", nullable = false)
  private EventDto<?> payload;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private KafkaOutboxStatus status;

  private int retryCount;

  private LocalDateTime nextRetryAt;
}
