package com.example.request_service.controller;

import com.example.request_service.DTO.request.CreateRequestDto;
import com.example.request_service.DTO.request.RequestFilter;
import com.example.request_service.DTO.request.RequestResponseDto;
import com.example.request_service.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requests")
public class RequestController {

  private final RequestService requestService;

  @PostMapping("")
  public RequestResponseDto create(
          @RequestBody CreateRequestDto createRequestDto
  ) {
    return requestService.create(createRequestDto);
  }

  @GetMapping("{id}")
  public RequestResponseDto getById(@PathVariable("id") UUID id) {
    return requestService.getById(id);
  }

  @GetMapping("")
  public List<RequestResponseDto> getAll(RequestFilter filter) {
    return requestService.getAll(filter);
  }

  @PutMapping("{id}/status")
  public RequestResponseDto updateStatus(
          @PathVariable("id") UUID id,
          @RequestParam String status
  ) {
    return requestService.updateStatus(id, status);
  }

  @PostMapping("{id}/assign")
  public void assign(
          @PathVariable("id") UUID requestId,
          @RequestParam UUID executorId
  ) {
    requestService.assign(requestId, executorId);
  }
}
