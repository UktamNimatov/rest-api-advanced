package com.epam.esm.dao;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EntityDao<T extends Entity> {

    Optional<T> findById(long id) throws DaoException;

    List<T> findAll(Pageable pageable) throws DaoException;

    void deleteById(long id) throws DaoException;

    T insert(T entity) throws DaoException;

    Optional<T> findByName(String name) throws DaoException;

}
