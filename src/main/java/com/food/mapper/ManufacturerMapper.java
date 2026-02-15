package com.food.mapper;

import com.food.dto.ManufacturerDto;
import com.food.model.Manufacturer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManufacturerMapper {

    ManufacturerDto toDto(Manufacturer manufacturer);

    Manufacturer toEntity(ManufacturerDto dto);
}
