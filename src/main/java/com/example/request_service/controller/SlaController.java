package com.example.request_service.controller;

import com.example.request_service.DTO.SlaRuleDto;
import com.example.request_service.enums.RequestPriority;
import com.example.request_service.service.SlaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sla")
public class SlaController {

  private final SlaService slaService;

  @PostMapping("")
  public SlaRuleDto create(@RequestBody SlaRuleDto slaRuleDto) {
    return slaService.create(slaRuleDto);
  }

  @GetMapping("")
  public List<SlaRuleDto> getAll() {
    return slaService.getAllSlaRules();
  }

  @GetMapping("rule")
  public SlaRuleDto getSlaRule(
          @RequestParam String categoryCode,
          @RequestParam String priority
  ) {
    return slaService.getSlaRule(
            categoryCode,
            RequestPriority.valueOf(priority.toUpperCase())
    );
  }
}
