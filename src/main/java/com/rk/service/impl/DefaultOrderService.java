package com.rk.service.impl;

import com.rk.ServiceLocator;
import com.rk.dao.OrderDao;
import com.rk.domain.Order;
import com.rk.service.OrderService;

import java.util.List;

public class DefaultOrderService implements OrderService {
    OrderDao orderDao;

    public DefaultOrderService() {
        orderDao = ServiceLocator.getBean("OrderDao");
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public List<Order> findOrderByUserId(long id) {
        return orderDao.findByUserId(id);
    }
}
