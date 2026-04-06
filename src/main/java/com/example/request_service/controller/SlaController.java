package com.example.request_service.controller;

import com.example.request_service.DTO.sla.SlaRequestDto;
import com.example.request_service.DTO.sla.SlaRuleDto;
import com.example.request_service.service.SlaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sla")
public class SlaController {

  private final SlaService slaService;

  @PostMapping
  public SlaRuleDto create(@Valid @RequestBody SlaRuleDto slaRuleDto) {
    return slaService.create(slaRuleDto);
  }

  @GetMapping
  public List<SlaRuleDto> getAll() {
    return slaService.getAllSlaRules();
  }

  @GetMapping("/rule")
  public SlaRuleDto getSlaRule(@Valid SlaRequestDto slaRequestDto) { return slaService.getSlaRule(slaRequestDto); }
}
