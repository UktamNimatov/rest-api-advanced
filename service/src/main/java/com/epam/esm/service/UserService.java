package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService<T> extends EntityService<User> {

    User findUserWithHighestOrder() throws ServiceException, ResourceNotFoundException;

}
