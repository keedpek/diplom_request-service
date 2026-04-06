package com.example.request_service.service;

import com.example.request_service.DTO.CategoryDto;

import java.util.List;

public interface CategoryService {
  CategoryDto create(CategoryDto categoryDto);
  List<CategoryDto> getAll();
  CategoryDto getByCode(String code);
}
