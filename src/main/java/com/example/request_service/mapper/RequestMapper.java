package com.example.request_service.mapper;

import com.example.request_service.DTO.request.CreateRequestDto;
import com.example.request_service.DTO.request.RequestResponseDto;
import com.example.request_service.entity.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RequestMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", expression = "java(com.example.request_service.enums.RequestStatus.NEW)")
  @Mapping(target = "priority", expression = "java(com.example.request_service.enums.RequestPriority.valueOf(createRequestDto.getPriority().toUpperCase()))")
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "deadline", ignore = true)
  @Mapping(target = "assignedToUserId", ignore = true)
  Request toEntity(CreateRequestDto createRequestDto);

  @Mapping(target = "status", expression = "java(request.getStatus().name())")
  @Mapping(target = "priority", expression = "java(request.getPriority().name())")
  @Mapping(target = "categoryCode", expression = "java(request.getCategory().getCode())")
  @Mapping(target = "categoryName", expression = "java(request.getCategory().getName())")
  RequestResponseDto toDto(Request request);
}
