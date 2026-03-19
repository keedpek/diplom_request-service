package com.example.request_service.service.serviceImpl;

import com.example.request_service.DTO.SlaRuleDto;
import com.example.request_service.entity.Category;
import com.example.request_service.entity.SlaRule;
import com.example.request_service.enums.RequestPriority;
import com.example.request_service.exceptions.NotFoundException;
import com.example.request_service.mapper.CategoryMapper;
import com.example.request_service.mapper.SlaRuleMapper;
import com.example.request_service.repository.CategoryRepository;
import com.example.request_service.repository.SlaRuleRepository;
import com.example.request_service.service.CategoryService;
import com.example.request_service.service.SlaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlaServiceImpl implements SlaService {

  private final CategoryService categoryService;
  private final SlaRuleRepository slaRuleRepository;
  private final CategoryRepository categoryRepository;
  private final SlaRuleMapper slaRuleMapper;
  private final CategoryMapper categoryMapper;

  @Override
  @Transactional
  public SlaRuleDto create(SlaRuleDto slaRuleDto) {
    Category category = categoryRepository.findByCode(slaRuleDto.getCategoryCode())
            .orElseThrow(() -> new NotFoundException("Категория не найдена"));

    SlaRule slaRule = slaRuleMapper.toEntity(slaRuleDto);
    slaRule.setCategory(category);

    return slaRuleMapper.toDto(slaRuleRepository.save(slaRule));
  }

  @Override
  public SlaRuleDto getSlaRule(String categoryCode, RequestPriority priority) {
    Category category = categoryRepository.findByCode(categoryCode)
            .orElseThrow(() -> new NotFoundException("Категория не найдена"));

    SlaRule rule = slaRuleRepository.findByCategoryAndPriority(category, priority).
            orElseThrow(() -> new NotFoundException("Правило не найдено"));

    return slaRuleMapper.toDto(rule);
  }

  @Override
  public List<SlaRuleDto> getAllSlaRules() {
    return slaRuleRepository.findAll().stream().map(slaRuleMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public LocalDateTime calculateDeadline(Category category, RequestPriority priority) {
    SlaRuleDto slaRuleDto = getSlaRule(category.getCode(), priority);
    return LocalDateTime.now().plusMinutes(slaRuleDto.getResponseTimeMinutes());
  }
}
