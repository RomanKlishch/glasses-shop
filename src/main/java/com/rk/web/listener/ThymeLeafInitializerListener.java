package com.rk.web.listener;

import com.rk.web.templator.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class ThymeLeafInitializerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        PageGenerator.instance().configureTemplate(servletContext);
        log.debug("Initialized generator of page");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
