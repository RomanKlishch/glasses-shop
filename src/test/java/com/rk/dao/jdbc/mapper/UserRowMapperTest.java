package com.rk.dao.jdbc.mapper;

import com.rk.dao.exception.JdbcException;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserRowMapperTest {
    UserRowMapper mapper;
    @Mock
    ResultSet resultSet ;

    @SneakyThrows
    @Test
    @DisplayName("Test mapper for entity user")
    void mapRow() {
        mapper = new UserRowMapper();

        when(resultSet.getLong("user_id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("o-o");
        when(resultSet.getString("email")).thenReturn("good");
        when(resultSet.getString("role")).thenReturn("ADMIN");

        User user = mapper.mapRow(resultSet);

        assertEquals(1L, user.getId().getId());
        assertEquals("o-o", user.getName());
        assertEquals("good", user.getEmail());
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test exception in mapper of user")
    void mapRow_Exception() {
        mapper = new UserRowMapper();
        when(resultSet.getLong("user_id")).thenThrow(SQLException.class);
        assertThrows(JdbcException.class, () -> mapper.mapRow(resultSet));
    }
}