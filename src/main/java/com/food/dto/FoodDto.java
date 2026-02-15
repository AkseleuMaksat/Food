package com.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDto {
    private Long id;
    private String name;
    private Integer calories;
    private Integer amounts;
    private Integer price;
    private ManufacturerDto manufacturer;
    private List<IngredientsDto> ingredients;
}
