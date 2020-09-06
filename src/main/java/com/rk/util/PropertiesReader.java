package com.rk.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesReader {
    private Properties properties;

    public PropertiesReader(String... path) {
        this.properties = setUpProperties(path);
    }

    public String getProperties(String name) {
        String value;
        if ((value = System.getenv(name)) != null) {
            return value;
        } else {
            return (String) properties.get(name);
        }
    }

    @SneakyThrows
    Properties setUpProperties(String[] paths) {
        Properties properties = new Properties();
        for (String path : paths) {
            try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(path)) {
                if (inputStream == null) {
                    log.error("path  does not exist - {}", path);
                    throw new IllegalArgumentException("No properties on path - ".concat(path));
                }
                properties.load(inputStream);
            }
        }
        return properties;
    }


//    private  File[] getResourceFolderFiles () {
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        URL url = loader.getResource("properties");
//        Path path = Paths.get(url.getPath()).normalize();
//        return path.toFile().listFiles();
//    }
}



