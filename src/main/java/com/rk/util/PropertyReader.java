package com.rk.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertyReader {
    private final Properties properties;

    public PropertyReader(String... path) {
        if ("PROD".equalsIgnoreCase(System.getenv("Profile"))) {
            this.properties = setUpProdProperties(path);
        } else {
            this.properties = setUpDevelopProperty();
        }
    }

    public String getProperty(String name) {
        String value;
        if ((value = properties.getProperty(name)) != null) {
            return value;
        }
        if ((value = System.getenv(name)) != null) {
            properties.put(name, value);
            return value;
        }
        return null;
    }

    @SneakyThrows
    Properties setUpProdProperties(String[] paths) {
        Properties properties = new Properties();
        for (String path : paths) {
            try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(path)) {
                if (inputStream == null) {
                    log.error("path  does not exist - {}", path);
                    throw new IllegalArgumentException("No properties on path - ".concat(path));
                }
                properties.load(inputStream);
            }
        }
        properties.put("jdbc.url", System.getenv("JDBC_DATABASE_URL"));
        properties.put("jdbc.user", System.getenv("JDBC_DATABASE_USERNAME"));
        properties.put("jdbc.password", System.getenv("JDBC_DATABASE_PASSWORD"));
        return properties;
    }

    @SneakyThrows
    Properties setUpDevelopProperty() {
        Properties properties = new Properties();
        String[] paths = new String[]{"src/main/resources/properties/dev.application.properties",
                "src/main/resources/properties/sqlQueries.properties",
                "src/test/resources/properties/test.properties"};
        for (String path : paths) {
            if (!new File(path).exists()) {
                log.error("path  does not exist - {}", path);
                throw new IllegalArgumentException("No properties on path - ".concat(path));
            }
            try (InputStream stream = new FileInputStream(path)) {
                properties.load(stream);
            }
        }
        return properties;
    }
}



