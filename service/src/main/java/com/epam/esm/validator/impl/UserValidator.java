package com.epam.esm.validator.impl;

import com.epam.esm.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String USERNAME_REGEX = "[\\p{Alpha}\\s*+\\p{Alpha}]{3,50}";

    public boolean checkUser(User user) {
        logger.info("is USERNAME null: " + (user.getName() == null));
        return user.getName() != null && Pattern.matches(USERNAME_REGEX, user.getName());
    }
}
