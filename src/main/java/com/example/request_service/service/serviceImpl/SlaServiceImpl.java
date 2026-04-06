package com.example.request_service.service.serviceImpl;

import com.example.request_service.DTO.sla.SlaRequestDto;
import com.example.request_service.DTO.sla.SlaRuleDto;
import com.example.request_service.entity.Category;
import com.example.request_service.entity.SlaRule;
import com.example.request_service.enums.RequestPriority;
import com.example.request_service.exceptions.NotFoundException;
import com.example.request_service.mapper.SlaRuleMapper;
import com.example.request_service.repository.CategoryRepository;
import com.example.request_service.repository.SlaRuleRepository;
import com.example.request_service.service.SlaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlaServiceImpl implements SlaService {

  private final SlaRuleRepository slaRuleRepository;
  private final CategoryRepository categoryRepository;
  private final SlaRuleMapper slaRuleMapper;

  @Override
  @Transactional
  public SlaRuleDto create(SlaRuleDto slaRuleDto) {
    log.info(
            "Создание SLA правила: category={}, priority={}",
            slaRuleDto.getCategoryCode(),
            slaRuleDto.getPriority()
    );

    Category category = getCategoryOrThrow(slaRuleDto.getCategoryCode());

    SlaRule slaRule = slaRuleMapper.toEntity(slaRuleDto);
    slaRule.setCategory(category);

    SlaRule slaRuleSaved = slaRuleRepository.save(slaRule);
    log.info("SLA правило создано: category={}, priority={}", slaRuleSaved.getCategory(), slaRuleSaved.getPriority());
    return slaRuleMapper.toDto(slaRuleSaved);
  }

  @Override
  public SlaRuleDto getSlaRule(SlaRequestDto slaRequestDto) {
    getSlaRuleLog(slaRequestDto.getCategoryCode(), RequestPriority.valueOf(slaRequestDto.getPriority()));

    Category category = getCategoryOrThrow(slaRequestDto.getCategoryCode());

    SlaRule rule = getSlaRuleOrThrow(
            category,
            RequestPriority.valueOf(slaRequestDto.getPriority().toUpperCase())
    );

    return slaRuleMapper.toDto(rule);
  }

  @Override
  public SlaRuleDto getSlaRule(String categoryCode, RequestPriority priority) {
    getSlaRuleLog(categoryCode, priority);

    Category category = getCategoryOrThrow(categoryCode);
    SlaRule rule = getSlaRuleOrThrow(category, priority);
    return slaRuleMapper.toDto(rule);
  }

  @Override
  public List<SlaRuleDto> getAllSlaRules() {
    log.debug("Получение всех SLA правил");
    return slaRuleRepository.findAll().stream().map(slaRuleMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public LocalDateTime calculateDeadline(Category category, RequestPriority priority) {
    log.debug("Расчет дедлайна: category={}, priority={}", category.getCode(), priority);
    SlaRuleDto slaRuleDto = getSlaRule(category.getCode(), priority);
    return LocalDateTime.now().plusMinutes(slaRuleDto.getExecutionTimeMinutes());
  }

  private Category getCategoryOrThrow(String categoryCode) {
    return categoryRepository.findByCode(categoryCode)
            .orElseThrow(() -> {
              log.warn("Категория не найдена: {}", categoryCode);
              return new NotFoundException("Категория не найдена");
            });
  }

  private SlaRule getSlaRuleOrThrow(Category category, RequestPriority priority) {
    return slaRuleRepository.findByCategoryAndPriority(category, priority)
            .orElseThrow(() -> {
              log.warn("SLA правило не найдено: category={}, priority={}", category.getCode(), priority);
              return new NotFoundException("Правило не найдено");
            });
  }

  private void getSlaRuleLog(String categoryCode, RequestPriority priority) {
    log.debug(
            "Получение SLA: category={}, priority={}",
            categoryCode,
            priority
    );
  }
}
