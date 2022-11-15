package com.epam.esm.service;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityService<T extends Entity> implements EntityService<T> {
    private static final Logger logger = LogManager.getLogger();

    private final AbstractEntityDao<T> abstractEntityDao;

    public AbstractEntityService(AbstractEntityDao<T> abstractEntityDao) {
        this.abstractEntityDao = abstractEntityDao;
    }

    @Override
    public Optional<T> findById(long id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<T> findAll(int page, int size) throws ServiceException {
        try {
            Pageable pageable = createPageRequest(page, size);
            logger.info("service layer. pageable is " + pageable.toString());
            logger.info("service layer result " + abstractEntityDao.findAll(pageable, new HashMap<>()).toString());
            logger.info("service layer result size " + abstractEntityDao.findAll(pageable, new HashMap<>()).size());
            return abstractEntityDao.findAll(pageable, new HashMap<>());
        }catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {

    }

    @Override
    public T insert(T entity) throws ServiceException {
        return null;
    }

    @Override
    public Optional<T> findByName(String name) throws ServiceException {
        return Optional.empty();
    }

    protected Pageable createPageRequest(int page, int size) throws ServiceException {
        Pageable pageRequest;
        try {
            pageRequest = PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e);
//            ExceptionResult exceptionResult = new ExceptionResult();
//            exceptionResult.addException(ExceptionMessageKey.INVALID_PAGINATION, page, size);
//            throw new IncorrectParameterException(exceptionResult);
        }
        return pageRequest;
    }
}
