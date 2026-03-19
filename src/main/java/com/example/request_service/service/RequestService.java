package com.example.request_service.service;

import com.example.request_service.DTO.request.CreateRequestDto;
import com.example.request_service.DTO.request.RequestFilter;
import com.example.request_service.DTO.request.RequestResponseDto;

import java.util.List;
import java.util.UUID;

public interface RequestService {
  RequestResponseDto create(CreateRequestDto createRequestDto);
  RequestResponseDto getById(UUID id);
  List<RequestResponseDto> getAll(RequestFilter filter);
  RequestResponseDto updateStatus(UUID id, String status);
  void assign(UUID requestId, UUID executorId);
}
