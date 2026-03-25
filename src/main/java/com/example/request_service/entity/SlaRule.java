package com.example.request_service.entity;

import com.example.request_service.enums.RequestPriority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sla_rules")
public class SlaRule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private short id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RequestPriority priority;

  @Column(nullable = false)
  private int responseTimeMinutes;

  @Column(nullable = false)
  private int executionTimeMinutes;
}
