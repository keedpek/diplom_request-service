package com.example.request_service.controller;

import com.example.request_service.DTO.request.*;
import com.example.request_service.service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/requests")
public class RequestController {

  private final RequestService requestService;

  @PostMapping
  public RequestResponseDto create(
          @Valid @RequestBody CreateRequestDto createRequestDto
  ) {
    return requestService.create(createRequestDto);
  }

  @GetMapping("/{id}")
  public RequestResponseDto getById(@PathVariable("id") UUID id) {
    return requestService.getById(id);
  }

  @GetMapping
  public List<RequestResponseDto> getAll(@Valid RequestFilter filter) {
    return requestService.getAll(filter);
  }

  @PatchMapping("/{id}/status")
  public RequestResponseDto updateStatus(
          @PathVariable("id") UUID id,
          @Valid @RequestBody UpdateRequestStatusDto updateRequestStatusDto
  ) {
    return requestService.updateStatus(id, updateRequestStatusDto);
  }
}
