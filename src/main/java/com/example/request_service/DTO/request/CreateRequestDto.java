package com.example.request_service.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateRequestDto {
  private String title;
  private String description;
  private String categoryCode;
  private String priority;
  private UUID createdByUserId;
}
