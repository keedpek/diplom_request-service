package com.example.request_service.repository;

import com.example.request_service.entity.KafkaOutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KafkaOutboxRepository extends JpaRepository<KafkaOutboxEvent, UUID> {

  @Modifying
  @Query(value = """
        UPDATE kafka_outbox
        SET status = 'PROCESSING'
        WHERE id IN (
            SELECT id FROM kafka_outbox
            WHERE status = 'NEW'
                AND (next_retry_at IS NULL OR next_retry_at <= now())
            ORDER BY created_at
            LIMIT :limit
            FOR UPDATE SKIP LOCKED
        )
        RETURNING *
    """, nativeQuery = true)
  List<KafkaOutboxEvent> lockBatch(int limit);
}
