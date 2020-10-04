package com.rk;

import com.rk.dao.GlassesDao;
import com.rk.dao.UserDao;
import com.rk.dao.jdbc.JdbcGlassesDao;
import com.rk.dao.jdbc.JdbcUserDao;
import com.rk.domain.User;
import com.rk.security.SecurityService;
import com.rk.security.impl.DefaultSecurityService;
import com.rk.service.GlassesService;
import com.rk.service.UserService;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.service.impl.UserServiceImpl;
import com.rk.util.PropertyReader;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceLocator {

    private static final Map<String, Object> CONTAINER = new HashMap<>();

    static {
        PropertyReader propertyReader = new PropertyReader("properties/application.properties", "properties/sqlQueries.properties");
        register("PropertyReader", propertyReader);

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(propertyReader.getProperty("jdbc.url"));
        dataSource.setUser(propertyReader.getProperty("jdbc.user"));
        dataSource.setPassword(propertyReader.getProperty("jdbc.password"));

        GlassesDao glassesDao = new JdbcGlassesDao(dataSource, propertyReader);
        register("GlassesDao", glassesDao);

        UserDao userDao = new JdbcUserDao(dataSource, propertyReader);
        register("UserDao", userDao);

        SecurityService securityService = new DefaultSecurityService();
        register("SecurityService", securityService);

        GlassesService glassesService = new DefaultGlassesService();
        register("GlassesService", glassesService);

        UserService userService = new UserServiceImpl();
        register("UserService", userService);

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

//TODO:
// 1) У Filter реально нет параметра order или я не смог его найти?
// 2) Как обрабатывать исключения в фильтрах и сервлеьах, что с ними делать?
// 3) Как реализовать доступ к роли пользователя с любой "page".html страницы?


//TODO: Если-да-кабы будет свободное время:
// 1) Представим ситуацию в уже готовый рабочий небольшой проект нужно добавить локализацию, как измениться БД
// 2) Jetty-Runner - deprecate - нужно заменить//