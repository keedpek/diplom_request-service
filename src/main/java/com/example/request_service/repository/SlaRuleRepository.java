package com.example.request_service.repository;

import com.example.request_service.entity.Category;
import com.example.request_service.entity.SlaRule;
import com.example.request_service.enums.RequestPriority;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlaRuleRepository extends JpaRepository<SlaRule, Short> {
  Optional<SlaRule> findByCategoryAndPriority(Category category, RequestPriority priority);

  @Override
  @EntityGraph(attributePaths = {"category"})
  List<SlaRule> findAll();
}
