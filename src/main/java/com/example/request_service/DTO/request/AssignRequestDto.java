package com.example.request_service.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AssignRequestDto {
  @NotNull(message = "Идентификатор исполнителя обязателен")
  private UUID executorId;
}
