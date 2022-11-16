package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.DaoException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderDao<T> extends EntityDao<Order> {

    List<Order> getOrdersOfUser(long userId, Pageable pageable) throws DaoException;

    Optional<Order> findMostExpensiveOrder() throws DaoException;

}
