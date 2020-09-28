package com.rk.web.listener;

import com.rk.domain.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

public class MapToServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Map<String, User> cookieTokens = new HashMap<>();
        event.getServletContext().setAttribute("cookieTokens",cookieTokens);
    }
}
