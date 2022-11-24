package com.epam.esm.service;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DaoException;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.validator.OrderValidator;
import com.epam.esm.validator.impl.OrderValidatorImpl;
import com.epam.esm.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
public class UserServiceImplTest {
    private static final Logger logger = LogManager.getLogger();

    @Mock
    private UserDao<User> userDao = Mockito.mock(UserDaoImpl.class);

    @Mock
    private AbstractEntityDao<User> abstractEntityDao = Mockito.mock(AbstractEntityDao.class);

    @Mock
    private UserValidator userValidator = Mockito.mock(UserValidator.class);

    @InjectMocks
    private UserServiceImpl userService;

    private static final User USER_1 = new User(1, "John Lennon");
    private static final User USER_2 = new User(2, "Mary Jane");
    private static final User USER_3 = new User(3, "Peter Parker");
    private static final User USER_4 = new User(4, "Antony Hopkins");
    private static final User USER_5 = new User(5, "Jenny Moore");

//    private static final Order ORDER_1 = new Order(1, 1324.44, "2020-10-10T02:23:11.122", USER_1, new ArrayList<>());
//    private static final Order ORDER_2 = new Order(2, 234.5, "2021-10-10T02:23:11.122", USER_2, new ArrayList<>());


    @Test
    @DisplayName("testing find all users method")
    public void testFindAll() throws DaoException {
        List<User> usersExpected = Arrays.asList(USER_1, USER_2, USER_3, USER_4);
        Mockito.when(abstractEntityDao.findAll(PageRequest.of(0, 10))).thenReturn(usersExpected);
        List<User> usersActual = userService.findAll(0, 10);
        Assertions.assertEquals(usersExpected, usersActual);
    }

    @Test
    @DisplayName("testing find user by id method")
    public void testFindById() {
        Mockito.when(abstractEntityDao.findById(2)).thenReturn(Optional.of(USER_2));
        User userActual = userService.findById(2);
        Assertions.assertEquals(USER_2, userActual);
    }

    @Test
    @DisplayName("testing find user by name method")
    public void testFindByName() {
        String name = "Peter Parker";
        Mockito.when(abstractEntityDao.findByName(name)).thenReturn(Optional.of(USER_3));
        User userActual = userService.findByName(name);
        Assertions.assertEquals(USER_3, userActual);
    }

    @Test
    @DisplayName("testing insert a new user method")
    public void testInsert() throws DaoException {
        Mockito.when(userDao.insert(USER_5)).thenReturn(USER_5);
        Mockito.when(userValidator.checkUser(USER_5)).thenReturn(true);
        User userActual = userService.insert(USER_5);
        Assertions.assertEquals(USER_5, userActual);
    }

    @Test
    @DisplayName("testing find user with the highest cost of orders method")
    public void test() throws DaoException {
        Mockito.when(userDao.findUserWithHighestOrder()).thenReturn(Optional.of(USER_1));
        User userActual = userService.findUserWithHighestOrder();
        Assertions.assertEquals(USER_1, userActual);
    }
}
