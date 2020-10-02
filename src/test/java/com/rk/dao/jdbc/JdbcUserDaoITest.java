package com.rk.dao.jdbc;

import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import com.rk.util.PropertyReader;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JdbcUserDaoITest {
    private final Flyway flyway;
    private final JdbcUserDao jdbcUserDao;
    private PropertyReader propertyReader = new PropertyReader();

    public JdbcUserDaoITest() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;");
        FluentConfiguration configuration = new FluentConfiguration();
        configuration.locations("db/migration/users");
        configuration.dataSource(jdbcDataSource);
        flyway = new Flyway(configuration);
        jdbcUserDao = new JdbcUserDao(jdbcDataSource, propertyReader);
    }

    @BeforeEach
    void init() {
        flyway.migrate();
    }

    @AfterEach
    void afterAll() {
        flyway.clean();
    }

    @Test
    void findById() {
        User userExpected = jdbcUserDao.findById(1L);
        User userActual = User.builder().id(new LongId<>(1L))
                .email("test-ADMIN").name("test-ADMIN")
                .password("test-ADMIN").sole("sole").role(UserRole.ADMIN).build();
        assertEquals(userActual, userExpected);
    }

    @Test
    void findAll() {
        List<User> glassesList = jdbcUserDao.findAll();
        assertEquals(3, glassesList.size());
    }

    @Test
    void save() {
        User userActual = User.builder()
                .email("test-test").name("test-test")
                .password("test-test").sole("sole").role(UserRole.ADMIN).build();
        jdbcUserDao.save(userActual);
        assertEquals(userActual, jdbcUserDao.findById(4));
    }

    @Test
    void update() {
        User userActual = User.builder().id(new LongId<>(1L))
                .email("test-test").name("test-test")
                .password("test-test").sole("sole").role(UserRole.ADMIN).build();
        jdbcUserDao.update(userActual);
        User userExpected = jdbcUserDao.findById(1L);
        assertEquals(userActual.getEmail(), userExpected.getEmail());
        assertEquals(userActual.getName(), userExpected.getName());
        assertEquals(userActual.getPassword(), userExpected.getPassword());
    }

    @Test
    void delete() {
        jdbcUserDao.delete(3);
        List<User> userList = jdbcUserDao.findAll();

        assertEquals(2, userList.size());
        assertEquals("test-ADMIN", userList.get(0).getName());
        assertEquals("test-USER", userList.get(1).getName());
    }

    @Test
    void findByLogin() {
        User userExpected = jdbcUserDao.findByLogin("test-ADMIN");
        User userActual = User.builder().id(new LongId<>(1L))
                .email("test-ADMIN").name("test-ADMIN")
                .password("test-ADMIN").sole("sole").role(UserRole.ADMIN).build();
        assertEquals(userActual, userExpected);
    }
}