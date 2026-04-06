package com.example.request_service.mapper;

import com.example.request_service.DTO.CategoryDto;
import com.example.request_service.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CategoryMapper {
  @Mapping(target = "id", ignore = true)
  Category toEntity(CategoryDto categoryDto);

  CategoryDto toDto(Category category);
}
