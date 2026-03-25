package com.example.request_service.entity;

import com.example.request_service.enums.RequestPriority;
import com.example.request_service.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "requests")
public class Request {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String title;

  private String description;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RequestStatus status;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RequestPriority priority;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Column(nullable = false)
  private UUID createdByUserId;

  private UUID assignedToUserId;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  private LocalDateTime deadline;
}

