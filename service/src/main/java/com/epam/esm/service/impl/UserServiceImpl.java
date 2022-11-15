package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractEntityService<User> implements UserService<User> {
    private final UserDao<User> userDao;

    @Autowired
    public UserServiceImpl(AbstractEntityDao<User> abstractEntityDao, UserDao<User> userDao) {
        super(abstractEntityDao);
        this.userDao = userDao;
    }
}
