package com.rk.dao;

import com.rk.domain.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findAll();

    List<Order> findByUserId(long id);

    void save(Order order);
}
