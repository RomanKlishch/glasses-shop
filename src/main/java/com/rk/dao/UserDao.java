package com.rk.dao;

import com.rk.domain.User;

import java.util.List;

public interface UserDao {
    User findById(long id);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(long id);
}
