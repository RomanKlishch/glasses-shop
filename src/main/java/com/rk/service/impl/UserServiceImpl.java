package com.rk.service.impl;

import com.rk.ServiceLocator;
import com.rk.dao.UserDao;
import com.rk.dao.jdbc.JdbcUserDao;
import com.rk.domain.User;
import com.rk.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = ServiceLocator.getBean(UserDao.class);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }
}
