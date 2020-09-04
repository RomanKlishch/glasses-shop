package com.rk.dao.jdbc.mapper;

import com.rk.domain.Glasses;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlassesRowMapperTest {

    @SneakyThrows
    @Test
    @DisplayName("Test mapper for entity glasses")
    void glassesMapRow() {
        GlassesRowMapper mapper = new GlassesRowMapper();
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getLong("glasses_id")).thenReturn(1l);
        when(resultSet.getString("name")).thenReturn("o-o");
        when(resultSet.getString("collection")).thenReturn("good");
        when(resultSet.getString("category")).thenReturn("sun");
        when(resultSet.getString("details")).thenReturn("ups");
        when(resultSet.getDouble("price")).thenReturn(20.00);

        Glasses actualGlasses = mapper.glassesMapRow(resultSet);

        assertEquals(1, actualGlasses.getGlassesId());
        assertEquals("o-o", actualGlasses.getName());
        assertEquals("good",actualGlasses.getCollection());
        assertEquals("sun",actualGlasses.getCategory());
        assertEquals("ups",actualGlasses.getDetails());
        assertEquals(20.00,actualGlasses.getPrice(),2);
    }
}
