package com.epam.esm.service;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DaoException;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.validator.OrderValidator;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.impl.OrderValidatorImpl;
import com.epam.esm.validator.impl.TagValidatorImpl;
import net.bytebuddy.description.NamedElement;
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

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    private static final Logger logger = LogManager.getLogger();

    @Mock
    private OrderDao<Order> orderDao = Mockito.mock(OrderDaoImpl.class);

    @Mock
    private AbstractEntityDao<Order> abstractEntityDao = Mockito.mock(AbstractEntityDao.class);

    @Mock
    private OrderValidator tagValidator = Mockito.mock(OrderValidatorImpl.class);

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final Order ORDER_1 = new Order(1, 234.5, "2021-10-10T02:23:11.122", new User(1, "Jony"), new ArrayList<>());
    private static final Order ORDER_2 = new Order(2, 956.30, "2022-04-19T12:20:11.122", new User(2, "Kino"), new ArrayList<>());
    private static final Order ORDER_3 = new Order(3, 12310.33, "2019-04-19T12:20:11.122", new User(3, "Funny"), new ArrayList<>());
    private static final Order ORDER_4 = new Order(4, 1000.98, "2012-04-19T12:20:11.122", new User(4, "Lilly"), new ArrayList<>());


    @Test
    @DisplayName(value = "testing find all method")
    public void testFindAll() throws DaoException {
        List<Order> ordersExpected = Arrays.asList(ORDER_1, ORDER_2, ORDER_3);
        Mockito.when(abstractEntityDao.findAll(PageRequest.of(0, 10))).thenReturn(ordersExpected);
        List<Order> ordersActual = orderService.findAll(0, 10);
        Assertions.assertEquals(ordersExpected, ordersActual);
    }

    @Test
    @DisplayName(value = "testing find by id method")
    public void testFindById() {
        Optional<Order> ordersExpected = Optional.of(ORDER_2);
        Mockito.when(abstractEntityDao.findById(2)).thenReturn(Optional.of(ORDER_2));
        Optional<Order> orderActual = Optional.of(orderService.findById(2));
        Assertions.assertEquals(ordersExpected, orderActual);
    }

    @Test
    @DisplayName(value = "testing find the most expensive order method")
    public void testFindMostExpensiveOrder() throws DaoException {
        Optional<Order> ordersExpected = Optional.of(ORDER_3);
        Mockito.when(orderDao.findMostExpensiveOrder()).thenReturn(Optional.of(ORDER_3));
        Optional<Order> orderActual = Optional.of(orderService.findMostExpensiveOrder());
        Assertions.assertEquals(ordersExpected, orderActual);
    }

    @Test
    @DisplayName(value = "testing find orders of user method")
    public void testFindOrdersOfUser() throws DaoException {
        List<Order> ordersExpected = Collections.singletonList(ORDER_2);
        Mockito.when(orderDao.getOrdersOfUser(1, PageRequest.of(0, 10))).thenReturn(ordersExpected);
        List<Order> ordersActual = orderService.getOrdersOfUser(1, 0, 10);
        Assertions.assertEquals(ordersExpected, ordersActual);
    }

    @Test
    @DisplayName(value = "testing find insert order method")
    public void testInsert() throws DaoException {
        Mockito.when(orderDao.insert(ORDER_4)).thenReturn(ORDER_4);
        Order orderActual = orderService.insert(ORDER_4);
        Assertions.assertEquals(ORDER_4, orderActual);
    }

}
