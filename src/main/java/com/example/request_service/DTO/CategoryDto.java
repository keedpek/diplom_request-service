package com.example.request_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {

  @NotBlank(message = "Код категории обязателен")
  @Size(max = 64, message = "Код категории не длиннее 64 символов")
  private String code;

  @NotBlank(message = "Название категории обязательно")
  @Size(max = 255, message = "Название не длиннее 255 символов")
  private String name;

  @Size(max = 500, message = "Описание не длиннее 500 символов")
  private String description;
}
