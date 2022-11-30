package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger();

    private final UserService<User> userService;
    private final DtoConverter<User, UserDto> userDtoConverter;
    private final Hateoas<UserDto> userHateoas;

    @Autowired
    public UserController(UserService<User> userService, DtoConverter<User, UserDto> userDtoConverter, Hateoas<UserDto> userHateoas) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.userHateoas = userHateoas;
    }

    @GetMapping
    public List<UserDto> getAll(@RequestParam(required = false) String name,
                             @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                             @RequestParam(value = "size", defaultValue = "5", required = false) int size) throws ServiceException {
        if (name != null) {
            return convertToDtoList(Collections.singletonList(userService.findByName(name)));
        }
        logger.info("in the controller layer: size " + userService.findAll(page, size).size());
        logger.info("in the controller layer: list: " + userService.findAll(page, size).toString());
        return convertToDtoList(userService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") long id) throws ResourceNotFoundException {
        UserDto userDto = userDtoConverter.convertToDto(userService.findById(id));
        userHateoas.addLinks(userDto);
        return userDto;
    }

    @GetMapping("/name/{name}")
    public UserDto findByName(@PathVariable("name") String name) throws ResourceNotFoundException, ServiceException {
        UserDto userDto = userDtoConverter.convertToDto(userService.findByName(name));
        userHateoas.addLinks(userDto);
        return userDto;
    }

    @GetMapping("/order/most-expensive")
    public UserDto findUserWithHighestOrder() {
        UserDto userDto = userDtoConverter.convertToDto(userService.findUserWithHighestOrder());
        userHateoas.addLinks(userDto);
        return userDto;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) throws ServiceException {
        userService.deleteById(id);
//        return ResponseEntity.status(HttpStatus.valueOf(204)).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> insertUser(@Valid @RequestBody UserDto userDto) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        UserDto userDtoResult =
                userDtoConverter.convertToDto(
                        userService.insert(userDtoConverter.convertToEntity(userDto)));
        userHateoas.addLinks(userDtoResult);
        return new ResponseEntity<>(userDtoResult, HttpStatus.CREATED);
    }


    private List<UserDto> convertToDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = userDtoConverter.convertToDto(user);
            userHateoas.addLinks(userDto);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
