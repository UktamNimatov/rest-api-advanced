package com.epam.esm.validator;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderValidator {

    boolean checkPrice(double price);
    boolean checkPurchaseTime(String purchaseTime);
    List<String> checkOrder(Order order);
}
