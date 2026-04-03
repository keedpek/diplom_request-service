package com.example.request_service.DTO.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateRequestStatusDto {
  @Pattern(
          regexp = "^(NEW|ASSIGNED|IN_PROGRESS|WAITING_FOR_RESPONSE|COMPLETED|CANCELLED)$",
          message = "Некорректный статус заявки"
  )
  private String status;
}
