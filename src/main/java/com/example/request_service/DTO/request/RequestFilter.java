package com.example.request_service.DTO.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RequestFilter {

  @Pattern(
          regexp = "^(NEW|ASSIGNED|IN_PROGRESS|WAITING_FOR_RESPONSE|COMPLETED|CANCELLED)$",
          message = "Некорректный статус заявки"
  )
  private String status;

  @Pattern(
          regexp = "^(LOW|MEDIUM|HIGH|CRITICAL)$",
          message = "Некорректный приоритет"
  )
  private String priority;

  @Size(max = 64, message = "Код категории не длиннее 64 символов")
  private String categoryCode;

  private UUID assignedToUserId;
}
