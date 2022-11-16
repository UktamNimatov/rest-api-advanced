package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.query_creator.QueryCreator;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.QueryTimeoutException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDaoImpl extends AbstractEntityDao<Order> implements OrderDao<Order> {
    private static final Logger logger = LogManager.getLogger();

    private static final String FIND_BY_USER_ID = "SELECT o FROM orders o WHERE o.user_id = :user_id";
    private static final String MOST_EXPENSIVE_ORDER = "FROM Order as o ORDER BY o.price DESC";
    private QueryCreator queryCreator;

    public OrderDaoImpl(QueryCreator queryCreator) {
        super(Order.class, queryCreator);
        this.queryCreator = queryCreator;
    }

    @Override
    protected String getTableName() {
        return "Order";
    }

    @Override
    public List<Order> getOrdersOfUser(long userId, Pageable pageable) throws DaoException {
        try {
            return entityManager.createQuery(FIND_BY_USER_ID, entityType)
                    .setParameter("user_id", userId)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        }catch (IllegalArgumentException | IllegalStateException | QueryTimeoutException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Order> findMostExpensiveOrder() throws DaoException {
        try {
            return entityManager.createQuery(MOST_EXPENSIVE_ORDER, entityType)
                    .setMaxResults(1)
                    .getResultList()
                    .stream().findFirst();
        }catch (IllegalArgumentException | NullPointerException e) {
            throw new DaoException(e);
        }
    }
}
