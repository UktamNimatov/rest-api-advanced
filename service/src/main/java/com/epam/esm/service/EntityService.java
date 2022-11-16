package com.epam.esm.service;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface EntityService<T extends Entity> {

    T findById(long id) throws ResourceNotFoundException;

    List<T> findAll(int page, int size) throws ServiceException;

    void deleteById(long id) throws ServiceException;

    T insert(T entity) throws ServiceException, InvalidFieldException, DuplicateResourceException;

    T findByName(String name) throws ResourceNotFoundException;

}
