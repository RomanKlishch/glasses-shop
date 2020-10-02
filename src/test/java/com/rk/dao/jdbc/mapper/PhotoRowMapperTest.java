package com.rk.dao.jdbc.mapper;

import com.rk.dao.exception.JdbcException;
import com.rk.domain.Photo;
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
class PhotoRowMapperTest {
    PhotoRowMapper mapper;
    @Mock
    private ResultSet resultSet;

    @Test
    @SneakyThrows
    @DisplayName("Test mapper for entity photo")
    void photoMapRow() {
        mapper = new PhotoRowMapper();
        when(resultSet.getLong("photo_id")).thenReturn(1L);
        when(resultSet.getString("path_to_image")).thenReturn("anyString");
        Photo photo = mapper.mapRow(resultSet);
        assertEquals(1, photo.getId());
        assertEquals("anyString", photo.getPathToImage());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test exception in mapper of photo")
    void mapRow_Exception() {
        mapper = new PhotoRowMapper();
        when(resultSet.getLong("photo_id")).thenThrow(SQLException.class);
        assertThrows(JdbcException.class, () -> mapper.mapRow(resultSet));
    }
}