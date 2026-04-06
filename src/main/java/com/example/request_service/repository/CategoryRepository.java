package com.example.request_service.repository;

import com.example.request_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Short> {
  boolean existsByCode(String code);
  Optional<Category> findByCode(String code);
}
