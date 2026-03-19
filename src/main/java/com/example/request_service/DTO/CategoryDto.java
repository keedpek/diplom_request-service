package com.example.request_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
  private String code;
  private String name;
  private String description;
}
