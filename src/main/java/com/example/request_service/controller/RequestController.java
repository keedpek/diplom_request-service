package com.example.request_service.controller;

import com.example.request_service.DTO.request.CreateRequestDto;
import com.example.request_service.DTO.request.RequestFilter;
import com.example.request_service.DTO.request.RequestResponseDto;
import com.example.request_service.service.RequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requests")
public class RequestController {

  private final RequestService requestService;

  @PostMapping
  public RequestResponseDto create(
          @Valid @RequestBody CreateRequestDto createRequestDto
  ) {
    return requestService.create(createRequestDto);
  }

  @GetMapping("{id}")
  public RequestResponseDto getById(@PathVariable("id") UUID id) {
    return requestService.getById(id);
  }

  @GetMapping
  public List<RequestResponseDto> getAll(@Valid RequestFilter filter) {
    return requestService.getAll(filter);
  }

  @PatchMapping("{id}/status")
  public RequestResponseDto updateStatus(
          @PathVariable("id") UUID id,
          @RequestParam
          @Pattern(
                  regexp = "^(NEW|ASSIGNED|IN_PROGRESS|WAITING_FOR_RESPONSE|COMPLETED|CANCELLED)$",
                  message = "Некорректный статус заявки"
          ) String status
  ) {
    return requestService.updateStatus(id, status);
  }

  @PostMapping("{id}/assign")
  public void assign(
          @PathVariable("id") UUID requestId,
          @RequestParam @NotNull(message = "Идентификатор исполнителя обязателен") UUID executorId
  ) {
    requestService.assign(requestId, executorId);
  }
}
