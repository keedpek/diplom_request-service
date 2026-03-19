package com.example.request_service.controller;

import com.example.request_service.DTO.CategoryDto;
import com.example.request_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping("")
  public CategoryDto create(@RequestBody CategoryDto categoryDto) {
    return categoryService.create(categoryDto);
  }

  @GetMapping("")
  public List<CategoryDto> getAll() {
    return categoryService.getAll();
  }

  @GetMapping("{code}")
  public CategoryDto getByCode(@PathVariable("code") String code) {
    return categoryService.getByCode(code);
  }
}
