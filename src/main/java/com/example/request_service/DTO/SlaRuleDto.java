package com.example.request_service.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlaRuleDto {

  @NotBlank(message = "Код категории обязателен")
  @Size(max = 64, message = "Код категории не длиннее 64 символов")
  private String categoryCode;

  @NotBlank(message = "Приоритет обязателен")
  @Pattern(
          regexp = "(?i)^(LOW|MEDIUM|HIGH|CRITICAL)$",
          message = "Приоритет: LOW, MEDIUM, HIGH или CRITICAL"
  )
  private String priority;

  @Min(value = 1, message = "Время реакции не менее 1 минуты")
  @Max(value = 525600, message = "Время реакции не больше 525600 минут (1 год)")
  private int responseTimeMinutes;

  @Min(value = 1, message = "Время выполнения не менее 1 минуты")
  @Max(value = 525600, message = "Время выполнения не больше 525600 минут (1 год)")
  private int executionTimeMinutes;
}
