package com.epam.esm.validator.impl;

import com.epam.esm.dao.mapper.ColumnName;
import com.epam.esm.entity.Order;
import com.epam.esm.validator.OrderValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.epam.esm.validator.impl.GiftCertificateValidatorImpl.INCORRECT_VALUE_PARAMETER;
import static com.epam.esm.validator.impl.GiftCertificateValidatorImpl.PRICE_REGEX;
import static java.time.ZonedDateTime.now;

@Component
public class OrderValidatorImpl implements OrderValidator {

    @Override
    public boolean checkPrice(double price) {
        return (price >= 0.0 && Pattern.matches(PRICE_REGEX, String.valueOf(price)));
    }

    @Override
    public boolean checkPurchaseTime(String purchaseTime) {
        try {
            return ZonedDateTime.ofLocal(LocalDateTime.parse(purchaseTime,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneOffset.UTC, ZoneOffset.UTC).isBefore(now());
        }catch (DateTimeParseException dateTimeParseException) {
            return false;
        }
    }

    @Override
    public List<String> checkOrder(Order order) {
        List<String> errorList = new ArrayList<>();
        if (!checkPrice(order.getPrice())) {
            errorList.add(ColumnName.PRICE + INCORRECT_VALUE_PARAMETER);
        }
        if (!checkPurchaseTime(order.getPurchaseTime())) {
            errorList.add(ColumnName.PURCHASE_TIME + INCORRECT_VALUE_PARAMETER);
        }
        return errorList;
    }
}
