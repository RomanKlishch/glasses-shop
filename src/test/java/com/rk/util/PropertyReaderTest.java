package com.rk.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyReaderTest {
    PropertyReader propertyReader = new PropertyReader();


    @Test
    @DisplayName("Find variables from global list in production")
    void getPropertiesGlobal() throws Exception {
        withEnvironmentVariable("Profile", "PROD")
                .and("TEST_JDBC_DATABASE_URL", "host:port")
                .and("TEST_JDBC_DATABASE_USERNAME", "sa")
                .and("TEST_JDBC_DATABASE_PASSWORD", "sa")
                .and("TEST_PORT", "8080").execute(() -> {
            propertyReader = new PropertyReader();
            assertEquals("host:port", propertyReader.getProperties("TEST_JDBC_DATABASE_URL"));
            assertEquals("sa", propertyReader.getProperties("TEST_JDBC_DATABASE_USERNAME"));
            assertEquals("sa", propertyReader.getProperties("TEST_JDBC_DATABASE_PASSWORD"));
            assertEquals("8080", propertyReader.getProperties("TEST_PORT"));
        });
    }

    @Test
    @DisplayName("Find variables from file in production")
    void getPropertiesFromFile() throws Exception {
        assertEquals("test_host:port", propertyReader.getProperties("TEST_JDBC_DATABASE_URL"));
        assertEquals("test_sa", propertyReader.getProperties("TEST_JDBC_DATABASE_USERNAME"));
        assertEquals("test_sa", propertyReader.getProperties("TEST_JDBC_DATABASE_PASSWORD"));
        assertEquals("test_8080", propertyReader.getProperties("TEST_PORT"));

    }

    @Test
    @DisplayName("Exception when path does not exist")
    void setUpProperties() {
        String[] path = new String[]{"ups"};
        assertThrows(IllegalArgumentException.class, () -> propertyReader.setUpProdProperties(path));
    }
}