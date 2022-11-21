package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger logger = LogManager.getLogger();

    private final OrderService<Order> orderService;
    private final DtoConverter<Order, OrderDto> orderDtoConverter;
    private final Hateoas<OrderDto> orderDtoHateoas;


    @Autowired
    public OrderController(OrderService<Order> orderService, DtoConverter<Order, OrderDto> orderDtoConverter, Hateoas<OrderDto> orderDtoHateoas) {
        this.orderService = orderService;
        this.orderDtoConverter = orderDtoConverter;
        this.orderDtoHateoas = orderDtoHateoas;
    }

    @GetMapping
    public List<OrderDto> findAll(@RequestParam(required = false) String userId,
                               @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                               @RequestParam(value = "size", defaultValue = "5", required = false) int size) throws ServiceException {
        if (userId != null) {
            return convertToDtoList(orderService.getOrdersOfUser(Long.parseLong(userId), page, size));
        }
        return convertToDtoList(orderService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable("id") long id) throws ResourceNotFoundException {
        OrderDto orderDto = orderDtoConverter.convertToDto(orderService.findById(id));
        orderDtoHateoas.addLinks(orderDto);
        return orderDto;
    }

    @GetMapping("/most-expensive")
    public OrderDto findMostExpensiveOrder() {
        OrderDto orderDto = orderDtoConverter.convertToDto(orderService.findMostExpensiveOrder());
        orderDtoHateoas.addLinks(orderDto);
        return orderDto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable long id) throws ServiceException {
        orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> insertOrder(@RequestBody OrderDto orderDto) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        OrderDto orderDtoResult =
                orderDtoConverter.convertToDto(
                        orderService.insert(orderDtoConverter.convertToEntity(orderDto)));
        orderDtoHateoas.addLinks(orderDtoResult);
        return new ResponseEntity<>(orderDtoResult, HttpStatus.CREATED);
    }

    private List<OrderDto> convertToDtoList(List<Order> orderList) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderDto orderDto = orderDtoConverter.convertToDto(order);
            orderDtoHateoas.addLinks(orderDto);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
