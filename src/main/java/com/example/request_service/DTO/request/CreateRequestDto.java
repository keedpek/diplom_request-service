package com.example.request_service.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateRequestDto {

  @NotBlank(message = "Заголовок обязателен")
  @Size(max = 255, message = "Заголовок не длиннее 255 символов")
  private String title;

  @Size(max = 3000, message = "Описание не длиннее 3000 символов")
  private String description;

  @NotBlank(message = "Код категории обязателен")
  @Size(max = 64, message = "Код категории не длиннее 64 символов")
  private String categoryCode;

  @NotBlank(message = "Приоритет обязателен")
  @Pattern(
          regexp = "(?i)^(LOW|MEDIUM|HIGH|CRITICAL)$",
          message = "Приоритет: LOW, MEDIUM, HIGH или CRITICAL"
  )
  private String priority;

  @NotNull(message = "Идентификатор автора обязателен")
  private UUID createdByUserId;
}
