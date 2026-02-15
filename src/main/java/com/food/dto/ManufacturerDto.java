package com.food.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerDto {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Code cannot be empty")
    private String code;
}
