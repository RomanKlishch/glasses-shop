package com.rk;

import com.rk.dao.jdbc.JdbcGlassesDao;
import com.rk.dao.jdbc.JdbcUserDao;
import com.rk.service.GlassesService;
import com.rk.service.UserService;
import com.rk.service.impl.DefaultGlassesService;
import com.rk.service.impl.UserServiceImpl;
import com.rk.util.PropertyReader;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator<T> {
    private static final Map<Class<?>, Object> CONTAINER = new HashMap<>();

    static {
        PropertyReader propertyReader = new PropertyReader("properties/application.properties","properties/sqlQueries.properties");
        register(PropertyReader.class,propertyReader);

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(propertyReader.getProperty("JDBC_DATABASE_URL"));
        dataSource.setUser(propertyReader.getProperty("JDBC_DATABASE_USERNAME"));
        dataSource.setPassword(propertyReader.getProperty("JDBC_DATABASE_PASSWORD"));

        JdbcGlassesDao jdbcGlassesDao = new JdbcGlassesDao(dataSource,propertyReader);
        register(JdbcGlassesDao.class,jdbcGlassesDao);

        JdbcUserDao jdbcUserDao = new JdbcUserDao(dataSource,propertyReader);
        register(JdbcUserDao.class,jdbcUserDao);

        GlassesService glassesService = new DefaultGlassesService();
        register(GlassesService.class,glassesService);

        UserService userService = new UserServiceImpl();
        register(UserService.class,userService);

    }

    public static<T> void register(Class<T> classBean, T bean) {
        CONTAINER.put(classBean,bean);
    }

    public static<T> T getBean(Class<T> classBean) {
        return (T) CONTAINER.get(classBean);
    }

}
