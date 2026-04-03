package com.example.request_service.DTO.sla;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SlaRequestDto {
  @NotBlank(message = "Код категории обязателен")
  @Size(max = 64, message = "Код категории не длиннее 64 символов")
  private String categoryCode;

  @NotBlank(message = "Приоритет обязателен")
  @Pattern(
          regexp = "(?i)^(LOW|MEDIUM|HIGH|CRITICAL)$",
          message = "Приоритет: LOW, MEDIUM, HIGH или CRITICAL"
  )
  private String priority;
}
