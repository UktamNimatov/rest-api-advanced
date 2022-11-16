package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.query_creator.QueryCreator;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl extends AbstractEntityDao<User> implements UserDao<User> {
    private static final Logger logger = LogManager.getLogger();

    private final QueryCreator queryCreator;
    private final OrderDao<Order> orderDao;

    private static final String FIND_USER_WITH_HIGHEST = "SELECT o.user_id FROM orders as o " +
            "GROUP BY o.user_id ORDER BY o.price DESC";

    public UserDaoImpl(QueryCreator queryCreator, OrderDao<Order> orderDao) {
        super(User.class, queryCreator);
        this.queryCreator = queryCreator;
        this.orderDao = orderDao;
    }

    @Override
    protected String getTableName() {
        return "User";
    }

    @Override
    public Optional<User> findUserWithHighestOrder() throws DaoException {
        Optional<Order> optionalOrder = orderDao.findMostExpensiveOrder();
        return optionalOrder.map(Order::getUser);
    }

    @Override
    public void deleteById(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
