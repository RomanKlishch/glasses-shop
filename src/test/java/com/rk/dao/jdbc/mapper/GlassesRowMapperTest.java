package com.rk.dao.jdbc.mapper;

import com.rk.dao.exception.JdbcException;
import com.rk.domain.Glasses;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GlassesRowMapperTest {
    GlassesRowMapper mapper;
    @Mock
    ResultSet resultSet;

    @SneakyThrows
    @Test
    @DisplayName("Test mapper for entity glasses")
    void glassesMapRow() {
        mapper = new GlassesRowMapper();
        when(resultSet.getLong("glasses_id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("o-o");
        when(resultSet.getString("collection")).thenReturn("good");
        when(resultSet.getString("category")).thenReturn("sun");
        when(resultSet.getString("details")).thenReturn("ups");
        when(resultSet.getDouble("price")).thenReturn(20.00);

        Glasses actualGlasses = mapper.mapRow(resultSet);

        assertEquals(1, actualGlasses.getId());
        assertEquals("o-o", actualGlasses.getName());
        assertEquals("good", actualGlasses.getCollection());
        assertEquals("sun", actualGlasses.getCategory());
        assertEquals("ups", actualGlasses.getDetails());
        assertEquals(20.00, actualGlasses.getPrice(), 2);
    }

    @SneakyThrows
    @Test
    @DisplayName("Test exception in mapper of glasses")
    void mapRow_Exception() {
        mapper = new GlassesRowMapper();
        when(resultSet.getLong("glasses_id")).thenThrow(SQLException.class);
        assertThrows(JdbcException.class, () -> mapper.mapRow(resultSet));
    }
}
