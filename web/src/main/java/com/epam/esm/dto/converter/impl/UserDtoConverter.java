package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements DtoConverter<User, UserDto> {

    @Override
    public User convertToEntity(UserDto dto) {
        User user = new User();

        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setOrders(dto.getOrderList());

        return user;
    }

    @Override
    public UserDto convertToDto(User entity) {
        UserDto userDto = new UserDto();

        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        userDto.setOrderList(entity.getOrders());

        return userDto;
    }
}
