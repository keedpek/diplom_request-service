package com.example.request_service.service;

import com.example.request_service.DTO.request.*;
import com.example.request_service.messaging.event.payload.ExecutorFoundEventDto;

import java.util.List;
import java.util.UUID;

public interface RequestService {
  RequestResponseDto create(CreateRequestDto createRequestDto);
  RequestResponseDto getById(UUID id);
  List<RequestResponseDto> getAll(RequestFilter filter);
  RequestResponseDto updateStatus(UUID id, UpdateRequestStatusDto updateRequestStatusDto);
  void assign(ExecutorFoundEventDto event);
}
