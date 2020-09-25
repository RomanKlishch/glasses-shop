package com.rk.web.listener;

import com.rk.web.templator.PageGenerator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ThymeLeafInitializerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        PageGenerator.configureTemplate(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
