package com.epam.esm.service;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface EntityService<T extends Entity> {

    Optional<T> findById(long id) throws ServiceException;

    List<T> findAll(int page, int size) throws ServiceException;

    void deleteById(long id) throws ServiceException;

    T insert(T entity) throws ServiceException;

    Optional<T> findByName(String name) throws ServiceException;

}
