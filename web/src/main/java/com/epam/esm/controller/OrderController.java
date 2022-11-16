package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService<Order> orderService;

    @Autowired
    public OrderController(OrderService<Order> orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> findAll(@RequestParam(required = false) String userId,
                               @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                               @RequestParam(value = "size", defaultValue = "5", required = false) int size) throws ServiceException {
        if (userId != null) {
            return orderService.getOrdersOfUser(Long.parseLong(userId), page, size);
        }
        return orderService.findAll(page, size);
    }
}
