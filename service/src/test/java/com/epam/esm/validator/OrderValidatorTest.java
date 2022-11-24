package com.epam.esm.validator;

import com.epam.esm.validator.impl.GiftCertificateValidatorImpl;
import com.epam.esm.validator.impl.OrderValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderValidatorTest {
    private OrderValidator validator;

    @BeforeEach
    public void init() {
        validator = new OrderValidatorImpl();
    }

    @Test
    @DisplayName(value = "Testing validity of the price of Order")
    public void testPrice() {
        double invalidPrice = -3.5;
        boolean result = validator.checkPrice(invalidPrice);
        Assertions.assertFalse(result);

    }

    @Test
    @DisplayName(value = "Testing validity of the purchase time of gift certficate")
    public void testPurchaseTime() {
        boolean test1 = validator.checkPurchaseTime("2023-11-11T16:34:24.024");
        Assertions.assertFalse(test1);
        boolean test2 = validator.checkPurchaseTime("2022-04-11T16:34:24.024");
        Assertions.assertTrue(test2);
    }

}
