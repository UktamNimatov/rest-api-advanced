package com.epam.esm.dto.converter.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoConverter implements DtoConverter<Order, OrderDto> {

    @Override
    public Order convertToEntity(OrderDto dto) {
        Order order = new Order();

        order.setId(dto.getId());
        order.setPrice(dto.getPrice());
        order.setPurchaseTime(dto.getPurchaseTime());

        User user = new User();
        user.setId(dto.getUserId());
        order.setUser(user);

//        order.setGiftCertificateList(dto.getGiftCertificateList());
        return order;
    }

    @Override
    public OrderDto convertToDto(Order entity) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(entity.getId());
        orderDto.setPrice(entity.getPrice());
        orderDto.setPurchaseTime(entity.getPurchaseTime());
        orderDto.setUserId(entity.getUser().getId());
//        orderDto.setGiftCertificateList(entity.getGiftCertificateList());

        return orderDto;
    }
}
