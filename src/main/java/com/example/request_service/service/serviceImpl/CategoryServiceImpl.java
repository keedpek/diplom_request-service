package com.example.request_service.service.serviceImpl;

import com.example.request_service.DTO.CategoryDto;
import com.example.request_service.entity.Category;
import com.example.request_service.exceptions.AlreadyExistsException;
import com.example.request_service.exceptions.NotFoundException;
import com.example.request_service.mapper.CategoryMapper;
import com.example.request_service.repository.CategoryRepository;
import com.example.request_service.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  @Transactional
  public CategoryDto create(CategoryDto categoryDto) {
    if(categoryRepository.existsByCode(categoryDto.getCode())) {
      throw new AlreadyExistsException("Категория уже существует");
    }
    Category category = categoryMapper.toEntity(categoryDto);
    return categoryMapper.toDto(categoryRepository.save(category));
  }

  @Override
  public List<CategoryDto> getAll() {
    return categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public CategoryDto getByCode(String code) {
    Category category = categoryRepository.findByCode(code)
            .orElseThrow(() -> new NotFoundException("Категория не найдена"));
    return categoryMapper.toDto(category);
  }
}
