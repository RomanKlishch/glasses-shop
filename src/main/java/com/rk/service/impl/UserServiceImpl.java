package com.rk.service.impl;

import com.rk.ServiceLocator;
import com.rk.dao.UserDao;
import com.rk.domain.User;
import com.rk.security.SecurityService;
import com.rk.service.UserService;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private SecurityService securityService;

    public UserServiceImpl() {
        this.userDao = ServiceLocator.getBean("UserDao");
        this.securityService = ServiceLocator.getBean("SecurityService");
    }

    @Override
    public void save(User user) {
        String sole = UUID.randomUUID().toString();
        String password = securityService.hash(user.getPassword(),sole);
        user.setSole(sole);
        user.setPassword(password);
        userDao.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
