package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger();
    private final UserService<User> userService;

    @Autowired
    public UserController(UserService<User> userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() throws ServiceException {
        logger.info("in the controller layer: size " + userService.findAll(1, 4).size());
        logger.info("in the controller layer: list: " + userService.findAll(1, 4).toString());
        return userService.findAll(0, 4);
    }
}
