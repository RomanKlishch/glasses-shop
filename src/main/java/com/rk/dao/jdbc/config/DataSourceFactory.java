package com.rk.dao.jdbc.config;

import com.rk.util.PropertyReader;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    private PropertyReader propertyReader;

    public DataSourceFactory(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(propertyReader.getProperty("jdbc.url"));
        dataSource.setUsername(propertyReader.getProperty("jdbc.user"));
        dataSource.setPassword(propertyReader.getProperty("jdbc.password"));
        dataSource.setDriverClassName(propertyReader.getProperty("driver.name"));
        dataSource.setInitialSize(4);
        return dataSource;
    }
}
