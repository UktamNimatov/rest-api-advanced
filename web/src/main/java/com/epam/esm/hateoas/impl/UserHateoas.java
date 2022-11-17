package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoas implements Hateoas<UserDto> {
    private static final Class<UserController> CONTROLLER = UserController.class;

    @Override
    public void addLinks(UserDto userDto) throws ResourceNotFoundException, ServiceException, InvalidFieldException, DuplicateResourceException {
        userDto.add(linkTo(methodOn(CONTROLLER)
                .findById(userDto.getId())).withRel("find by id"));
        userDto.add(linkTo(methodOn(CONTROLLER)
                .deleteUser(userDto.getId())).withRel("delete"));
        userDto.add(linkTo(methodOn(CONTROLLER)
                .findByName(userDto.getName())).withRel("find by name"));
        userDto.add(linkTo(methodOn(CONTROLLER)
                .insertUser(userDto)).withRel("insert new"));
    }
}
