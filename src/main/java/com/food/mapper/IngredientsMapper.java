package com.food.mapper;

import com.food.dto.IngredientsDto;
import com.food.model.Ingredients;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientsMapper {

    IngredientsDto toDto(Ingredients ingredients);

    Ingredients toEntity(IngredientsDto dto);
}
