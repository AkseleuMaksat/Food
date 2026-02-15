package com.food.mapper;

import com.food.dto.UserDto;
import com.food.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserDto dto);
}
