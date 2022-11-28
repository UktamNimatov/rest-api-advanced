package com.epam.esm.service.impl;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.*;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractEntityService<User> implements UserService<User> {
    private static final Logger logger = LogManager.getLogger();

    private final UserDao<User> userDao;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(AbstractEntityDao<User> abstractEntityDao, UserDao<User> userDao, UserValidator userValidator) {
        super(abstractEntityDao);
        this.userDao = userDao;
        this.userValidator = userValidator;
    }

    @Override
    public User insert(User user) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        try {
            if (!userValidator.checkUser(user)) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_USERNAME);
            }
            if (userDao.findByName(user.getName()).isPresent()) {
                throw new DuplicateResourceException(String.valueOf(ConstantMessages.ERROR_CODE_409),
                        ConstantMessages.EXISTING_USERNAME);
            }
            return userDao.insert(user);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public User findUserWithHighestOrder() throws ServiceException, ResourceNotFoundException {
        try {
            if (!userDao.findUserWithHighestOrder().isPresent()) {
                throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                        ConstantMessages.RESOURCE_NOT_FOUND);
            } else return userDao.findUserWithHighestOrder().get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException(ConstantMessages.OPERATION_NOT_SUPPORTED);
    }
}
