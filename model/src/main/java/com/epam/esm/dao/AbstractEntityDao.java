package com.epam.esm.dao;

import com.epam.esm.dao.query_creator.QueryCreator;
import com.epam.esm.entity.Entity;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository
@Transactional
public abstract class AbstractEntityDao<T extends Entity> implements EntityDao<T>{
    private static final Logger logger = LogManager.getLogger();

    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<T> entityType;
    private final QueryCreator queryCreator;

    public AbstractEntityDao(Class<T> entityType, QueryCreator queryCreator) {
        this.entityType = entityType;
        this.queryCreator = queryCreator;
    }

    protected abstract String getTableName();

    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String SELECT_FROM = "FROM ";
    private static final String WHERE_ID = " WHERE id=?";
    private static final String WHERE_NAME = " WHERE name=:name";
    private static final String WHERE_NAME_WITH_PARAMETER = " WHERE name=:name";
    private static final String DELETE_FROM = " DELETE FROM ";


    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    @Override
    public List<T> findAll(Pageable pageable) throws DaoException {
        try {
            logger.info("the abstract entity dao layer. Current query is " + SELECT_FROM + getTableName());
            logger.info("entity type simple name: " + entityType.getSimpleName());
            TypedQuery<T> queryForFindAll = entityManager.createQuery(SELECT_FROM + getTableName(), entityType);
            logger.info("Created typed query result is " + queryForFindAll.getResultList().toString());
            logger.info("result size is : " + queryForFindAll.getResultList().size());
            return queryForFindAll
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        }catch (IllegalArgumentException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteById(long id) throws DaoException {
        try {
            T entity = entityManager.find(entityType, id);
            entityManager.remove(entity);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public T insert(T entity) throws DaoException {
        try {
            entityManager.persist(entity);
            return entity;
        }catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<T> findByName(String name) {
        try {
            logger.info("abstract entity dao: find by name: " + name);
            TypedQuery<T> query = entityManager.createQuery(SELECT_FROM + getTableName() + WHERE_NAME, entityType);
            query.setParameter("name", name);
            return query.getResultList().stream().findFirst();
        } catch (DataAccessException e) {
            logger.error("error in getting one object from database");
            return Optional.empty();
        }
    }

}
