package com.example.request_service.service;

import com.example.request_service.DTO.SlaRuleDto;
import com.example.request_service.entity.Category;
import com.example.request_service.enums.RequestPriority;

import java.time.LocalDateTime;
import java.util.List;

public interface SlaService {
  SlaRuleDto create(SlaRuleDto slaRuleDto);
  SlaRuleDto getSlaRule(String categoryCode, RequestPriority priority);
  List<SlaRuleDto> getAllSlaRules();
  LocalDateTime calculateDeadline(Category category, RequestPriority priority);
}
