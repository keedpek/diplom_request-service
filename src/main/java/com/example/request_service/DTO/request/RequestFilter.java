package com.example.request_service.DTO.request;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestFilter {
  private String status;
  private String priority;
  private String categoryCode;
  private UUID assignedToUserId;
}
