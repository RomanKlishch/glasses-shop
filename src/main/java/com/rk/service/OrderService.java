package com.rk.service;

import com.rk.domain.Order;

import java.util.List;

public interface OrderService {
    void save(Order order);

    List<Order> findOrderByUserId(long id);
}
