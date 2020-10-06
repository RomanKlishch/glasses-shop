package com.rk.web.listener;

import com.rk.security.entity.Session;
import com.rk.web.util.SessionCleaner;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SessionTokensMapInitializer implements ServletContextListener {
    ScheduledExecutorService executorService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        Map<String, Session> sessionTokens = new ConcurrentHashMap<>();
        event.getServletContext().setAttribute("sessionTokens", sessionTokens);
        log.debug("Created sessionTokens Map");

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new SessionCleaner(sessionTokens), 0, 6, TimeUnit.HOURS);
    }
}
