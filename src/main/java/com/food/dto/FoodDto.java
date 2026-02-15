package com.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDto {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Positive(message = "Calories must be positive")
    private Integer calories;
    @Positive(message = "Amounts must be positive")
    private Integer amounts;
    @Positive(message = "Price must be positive")
    private Integer price;
    private ManufacturerDto manufacturer;
    private List<IngredientsDto> ingredients;
}
