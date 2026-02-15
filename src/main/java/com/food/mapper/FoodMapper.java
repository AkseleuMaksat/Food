package com.food.mapper;

import com.food.dto.FoodDto;
import com.food.model.Foods;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ManufacturerMapper.class, IngredientsMapper.class })
public interface FoodMapper {
    FoodDto toDto(Foods food);
    Foods toEntity(FoodDto dto);
}
