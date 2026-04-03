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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  @Transactional
  public CategoryDto create(CategoryDto categoryDto) {
    log.info("Создание категории: code={}", categoryDto.getCode());

    if(categoryRepository.existsByCode(categoryDto.getCode())) {
      log.warn("Категория уже существует: code={}", categoryDto.getCode());
      throw new AlreadyExistsException("Категория уже существует");
    }
    Category category = categoryMapper.toEntity(categoryDto);
    Category savedCategory = categoryRepository.save(category);
    log.info("Категория создана: code={}", savedCategory.getCode());
    return categoryMapper.toDto(savedCategory);
  }

  @Override
  public List<CategoryDto> getAll() {
    log.info("Получение списка категорий");
    return categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public CategoryDto getByCode(String code) {
    log.info("Получение категории: code={}", code);
    Category category = categoryRepository.findByCode(code)
            .orElseThrow(() -> {
              log.warn("Категория не найдена: code={}", code);
              return new NotFoundException("Категория не найдена");
            });
    return categoryMapper.toDto(category);
  }
}
