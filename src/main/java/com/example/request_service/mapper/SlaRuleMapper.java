package com.example.request_service.mapper;

import com.example.request_service.DTO.SlaRuleDto;
import com.example.request_service.entity.SlaRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SlaRuleMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "priority", expression = "java(com.example.request_service.enums.RequestPriority.valueOf(slaRuleDto.getPriority().toUpperCase()))")
  SlaRule toEntity(SlaRuleDto slaRuleDto);

  @Mapping(target = "priority", expression = "java(entity.getPriority().name())")
  @Mapping(target = "categoryCode", expression = "java(entity.getCategory().getCode())")
  SlaRuleDto toDto(SlaRule entity);
}
