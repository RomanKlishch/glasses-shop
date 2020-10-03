package com.rk.security.impl;

import com.rk.ServiceLocator;
import com.rk.dao.UserDao;
import com.rk.domain.User;
import com.rk.security.SecurityService;
import com.rk.security.entity.Session;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class DefaultSecurityService implements SecurityService {
    private UserDao userDao;

    public DefaultSecurityService() {
        this.userDao = ServiceLocator.getBean("UserDao");
    }

    @Override
    public String hash(String sole, String password) {
        String solePassword = sole.concat(password);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(solePassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : digest.digest()) {
                builder.append(b);
            }
            return String.valueOf(builder);
        } catch (NoSuchAlgorithmException e) {
            log.error("Cannot find algorithm -", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User login(String login, String uaPassword) {
        User user = userDao.findByLogin(login);
        if (user != null) {
            String encodeUaPassword = hash(user.getSole(), uaPassword);
            if (encodeUaPassword.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
