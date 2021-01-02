package com.rk.web.config;


import com.rk.dao.GlassesDao;
import com.rk.dao.OrderDao;
import com.rk.dao.UserDao;
import com.rk.dao.jdbc.JdbcGlassesDao;
import com.rk.dao.jdbc.JdbcOrder;
import com.rk.dao.jdbc.JdbcUserDao;
import com.rk.domain.User;
import com.rk.security.SecurityService;
import com.rk.security.entity.Session;
import com.rk.security.impl.DefaultSecurityService;
import com.rk.service.GlassesService;
import com.rk.service.OrderService;
import com.rk.service.UserService;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.service.impl.DefaultOrderService;
import com.rk.service.impl.UserServiceImpl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ComponentScan("com.rk")
@PropertySource({"classpath:properties/application.properties"})
public class ConfigureApplication {

    @Bean
    protected DataSource dataSource(@Value("${jdbc.url}") String url,
                                    @Value("${jdbc.user}") String userName,
                                    @Value("${jdbc.password}") String password,
                                    @Value("${jdbc.driver}") String driverClassName,
                                    @Value("${connections.amount}") int initialSize) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);
        return dataSource;
    }

    @Bean
    protected GlassesDao glassesDao(DataSource dataSource) {
        return new JdbcGlassesDao(dataSource);
    }

    @Bean
    protected OrderDao orderDao(DataSource dataSource) {
        return new JdbcOrder(dataSource);
    }

    @Bean
    protected UserDao userDao(DataSource dataSource) {
        return new JdbcUserDao(dataSource);
    }

    @Bean
    protected SecurityService securityService(UserDao userDao) {
        return new DefaultSecurityService(userDao);
    }

    @Bean
    protected GlassesService glassesService(GlassesDao glassesDao) {
        return new DefaultGlassesService(glassesDao);
    }

    @Bean
    protected UserService userService(UserDao userDao, SecurityService securityService) {
        return new UserServiceImpl(userDao,securityService);
    }

    @Bean
    protected OrderService orderService(OrderDao orderDao) {
        return new DefaultOrderService(orderDao);
    }

    @Bean
    protected Map<String, User> userTokens() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    protected Map<String, Session> sessionTokens() {
        return new ConcurrentHashMap<>();
    }
}


