package com.rk.web.listener;

import com.rk.security.entity.Session;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserTokensMapInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Map<String, Session> userTokens = new ConcurrentHashMap<>();
        event.getServletContext().setAttribute("userTokens", userTokens);
    }
}
