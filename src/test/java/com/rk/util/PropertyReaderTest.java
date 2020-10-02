package com.rk.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyReaderTest {
    private PropertyReader propertyReader = new PropertyReader();

    @Test
    @DisplayName("Find variables from global list in production")
    void getPropertiesGlobal() throws Exception {
        withEnvironmentVariable("Profile", "PROD")
                .and("JDBC_DATABASE_URL", "host:port")
                .and("JDBC_DATABASE_USERNAME", "sa")
                .and("JDBC_DATABASE_PASSWORD", "sa")
                .and("PORT", "8080").execute(() -> {
            propertyReader = new PropertyReader();
            assertEquals("host:port", propertyReader.getProperty("jdbc.url"));
            assertEquals("sa", propertyReader.getProperty("jdbc.user"));
            assertEquals("sa", propertyReader.getProperty("jdbc.password"));
            assertEquals("8080", propertyReader.getProperty("PORT"));
        });
    }

    @Test
    @DisplayName("Find variables from file in production")
    void getPropertiesFromFile() {
        assertEquals("test_host:port", propertyReader.getProperty("test_jdbc.url"));
        assertEquals("test_sa", propertyReader.getProperty("test_jdbc.user"));
        assertEquals("test_sa", propertyReader.getProperty("test_jdbc.password"));
        assertEquals("test_8080", propertyReader.getProperty("test_port"));
    }

    @Test
    @DisplayName("Exception when path does not exist")
    void setUpProperties() {
        String[] path = new String[]{"ups"};
        assertThrows(IllegalArgumentException.class, () -> propertyReader.setUpProdProperties(path));
    }
}