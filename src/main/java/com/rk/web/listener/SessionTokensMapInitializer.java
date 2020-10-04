package com.rk.web.listener;

import com.rk.security.entity.Session;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionTokensMapInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Map<String, Session> sessionTokens = new ConcurrentHashMap<>();
        event.getServletContext().setAttribute("sessionTokens", sessionTokens);
        log.debug("Created sessionTokens Map");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
