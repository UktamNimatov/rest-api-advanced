package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService<T> extends EntityService<Order> {

    List<Order> getOrdersOfUser(long userId, int page, int size) throws ServiceException;

    Order findMostExpensiveOrder() throws ServiceException, ResourceNotFoundException;

}
