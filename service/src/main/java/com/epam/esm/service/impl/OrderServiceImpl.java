package com.epam.esm.service.impl;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.*;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends AbstractEntityService<Order> implements OrderService<Order> {
    private static final Logger logger = LogManager.getLogger();

    private final OrderDao<Order> orderDao;
    private final OrderValidator orderValidator;

    @Autowired
    public OrderServiceImpl(AbstractEntityDao<Order> abstractEntityDao, OrderDao<Order> orderDao, OrderValidator orderValidator) {
        super(abstractEntityDao);
        this.orderDao = orderDao;
        this.orderValidator = orderValidator;
    }

    @Override
    public Order insert(Order order) throws ServiceException, InvalidFieldException {
        try {
            if (!orderValidator.checkOrder(order).isEmpty())
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE +
                                orderValidator.checkOrder(order).toString());
            return orderDao.insert(order);
        }catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Order> getOrdersOfUser(long userId, int page, int size) throws ServiceException {
        try {
            return orderDao.getOrdersOfUser(userId, createPageRequest(page, size));
        }catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public Order findMostExpensiveOrder() throws ServiceException, ResourceNotFoundException {
        try {
            if (!orderDao.findMostExpensiveOrder().isPresent()) {
                throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                        ConstantMessages.RESOURCE_NOT_FOUND);
            }
            return orderDao.findMostExpensiveOrder().get();
        }catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
