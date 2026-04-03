package com.example.request_service.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class RequestResponseDto {
  private UUID id;
  private String title;
  private String description;
  private String status;
  private String priority;
  private String categoryCode;
  private String categoryName;
  private LocalDateTime createdAt;
  private LocalDateTime deadline;
}
