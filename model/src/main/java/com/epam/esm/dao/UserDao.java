package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DaoException;

import java.util.Optional;

public interface UserDao<T> extends EntityDao<User> {

    Optional<User> findUserWithHighestOrder() throws DaoException;

}
