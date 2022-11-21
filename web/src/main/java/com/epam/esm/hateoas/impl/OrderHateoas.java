package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderHateoas implements Hateoas<OrderDto> {
    private static final Class<OrderController> CONTROLLER = OrderController.class;

    @Override
    public void addLinks(OrderDto orderDto) throws ResourceNotFoundException, ServiceException, InvalidFieldException, DuplicateResourceException {
        orderDto.add(linkTo(methodOn(CONTROLLER)
                .findById(orderDto.getId())).withRel("find by id"));
        orderDto.add(linkTo(methodOn(CONTROLLER)
                .deleteOrder(orderDto.getId())).withRel("delete"));
        orderDto.add(linkTo(methodOn(CONTROLLER)
                .insertOrder(orderDto)).withRel("insert new"));
        orderDto.add(linkTo(methodOn(CONTROLLER)
                .findMostExpensiveOrder()).withRel("find most expensive order"));
    }
}
