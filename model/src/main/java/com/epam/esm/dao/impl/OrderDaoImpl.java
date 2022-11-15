package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.query_creator.QueryCreator;
import com.epam.esm.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class OrderDaoImpl extends AbstractEntityDao<Order> implements OrderDao<Order> {
    private static final Logger logger = LogManager.getLogger();

    private static final String FIND_BY_USER_ID = "SELECT o FROM orders o WHERE o.user_id = :user_id";
    private QueryCreator queryCreator;

    public OrderDaoImpl(QueryCreator queryCreator) {
        super(Order.class, queryCreator);
        this.queryCreator = queryCreator;
    }

    @Override
    protected String getTableName() {
        return "orders";
    }

    @Override
    public List<Order> getOrdersOfUser(long userId, Pageable pageable) {
        return entityManager.createQuery(FIND_BY_USER_ID, entityType)
                .setParameter("user_id", userId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}
