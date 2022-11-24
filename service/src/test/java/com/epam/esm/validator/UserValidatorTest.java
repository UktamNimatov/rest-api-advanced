package com.epam.esm.validator;

import com.epam.esm.entity.User;
import com.epam.esm.validator.impl.TagValidatorImpl;
import com.epam.esm.validator.impl.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserValidatorTest {
    private UserValidator validator;

    @BeforeEach
    public void init() {
        validator = new UserValidator();
    }

    @Test
    @DisplayName(value = "Testing validity of the price of Order")
    public void testName() {
        String invalidName = "Uktam234";
        User user = new User(invalidName);
        boolean result = validator.checkUser(user);
        Assertions.assertFalse(result);
    }

}
