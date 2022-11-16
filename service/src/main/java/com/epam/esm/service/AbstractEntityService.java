package com.epam.esm.service;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.entity.Entity;
import com.epam.esm.exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.xml.ws.Service;
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
    public T findById(long id) throws ResourceNotFoundException {
        if (!abstractEntityDao.findById(id).isPresent()) {
            throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                    ConstantMessages.RESOURCE_NOT_FOUND);
        }
        return abstractEntityDao.findById(id).get();
    }

    @Override
    public List<T> findAll(int page, int size) throws ServiceException {
        try {
            Pageable pageable = createPageRequest(page, size);
            logger.info("service layer. pageable is " + pageable.toString());
            logger.info("service layer result " + abstractEntityDao.findAll(pageable).toString());
            logger.info("service layer result size " + abstractEntityDao.findAll(pageable).size());
            return abstractEntityDao.findAll(pageable);
        }catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            abstractEntityDao.deleteById(id);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public T insert(T entity) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        try {
            return abstractEntityDao.insert(entity);
        }catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public T findByName(String name) throws ResourceNotFoundException {
        logger.info("abstract entity service: name: " + name);
        if (!abstractEntityDao.findByName(name).isPresent()) {
            throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                    ConstantMessages.RESOURCE_NOT_FOUND);
        }
        return abstractEntityDao.findByName(name).get();
    }

    protected Pageable createPageRequest(int page, int size) throws ServiceException {
        try {
            return PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(ConstantMessages.INVALID_PAGINATION);
        }
    }
}
