package com.example.request_service.controller;

import com.example.request_service.DTO.SlaRuleDto;
import com.example.request_service.enums.RequestPriority;
import com.example.request_service.service.SlaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sla")
@Validated
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

  @GetMapping("rule")
  public SlaRuleDto getSlaRule(
          @RequestParam @NotBlank(message = "Код категории обязателен") @Size(
                  max = 64,
                  message = "Код категории не длиннее 64 символов") String categoryCode,
          @RequestParam @NotBlank(message = "Приоритет обязателен") @Pattern(
                  regexp = "(?i)^(LOW|MEDIUM|HIGH|CRITICAL)$",
                  message = "Приоритет: LOW, MEDIUM, HIGH или CRITICAL") String priority
  ) {
    return slaService.getSlaRule(
            categoryCode,
            RequestPriority.valueOf(priority.toUpperCase())
    );
  }
}
