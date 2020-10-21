package com.rk;

import com.rk.dao.GlassesDao;
import com.rk.dao.OrderDao;
import com.rk.dao.UserDao;
import com.rk.dao.jdbc.JdbcGlassesDao;
import com.rk.dao.jdbc.JdbcOrder;
import com.rk.dao.jdbc.JdbcUserDao;
import com.rk.dao.jdbc.config.DataSourceFactory;
import com.rk.domain.User;
import com.rk.security.SecurityService;
import com.rk.security.impl.DefaultSecurityService;
import com.rk.service.GlassesService;
import com.rk.service.OrderService;
import com.rk.service.UserService;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.service.impl.DefaultOrderService;
import com.rk.service.impl.UserServiceImpl;
import com.rk.util.PropertyReader;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceLocator {

    private static final Map<String, Object> CONTAINER = new HashMap<>();

    static {
        PropertyReader propertyReader = new PropertyReader("properties/application.properties", "properties/sqlQueries.properties");
        register("PropertyReader", propertyReader);

        DataSource dataSource = new DataSourceFactory(propertyReader).getDataSource();
        register("DataSource", dataSource);

        GlassesDao glassesDao = new JdbcGlassesDao(dataSource, propertyReader);
        register("GlassesDao", glassesDao);

        UserDao userDao = new JdbcUserDao(dataSource, propertyReader);
        register("UserDao", userDao);

        OrderDao orderDao = new JdbcOrder(dataSource, propertyReader);
        register("OrderDao", orderDao);

        SecurityService securityService = new DefaultSecurityService();
        register("SecurityService", securityService);

        GlassesService glassesService = new DefaultGlassesService();
        register("GlassesService", glassesService);

        UserService userService = new UserServiceImpl();
        register("UserService", userService);

        OrderService orderService = new DefaultOrderService();
        register("OrderService", orderService);

        Map<String, User> userTokens = new ConcurrentHashMap<>();
        register("UserTokens", userTokens);
    }

    public static <T> void register(String nameBean, T bean) {
        CONTAINER.put(nameBean, bean);
    }

    public static <T> T getBean(String nameBean) {
        return (T) CONTAINER.get(nameBean);
    }
}