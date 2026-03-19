package com.example.request_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlaRuleDto {
  private String categoryCode;
  private String priority;
  private int responseTimeMinutes;
  private int executionTimeMinutes;
}
