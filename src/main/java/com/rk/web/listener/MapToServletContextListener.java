package com.rk.web.listener;

import com.rk.domain.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapToServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Map<String, User> cookieTokens = new ConcurrentHashMap<>();
        event.getServletContext().setAttribute("cookieTokens", cookieTokens);
    }
}
