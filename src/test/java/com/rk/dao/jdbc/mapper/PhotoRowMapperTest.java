package com.rk.dao.jdbc.mapper;

import com.rk.domain.Photo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoRowMapperTest {
    @Mock
    ResultSet resultSet;

    @Test
    @SneakyThrows
    @DisplayName("Test mapper for entity photo")
    void photoMapRow(){
        PhotoRowMapper mapper = new PhotoRowMapper();

        when(resultSet.getLong("photo_id")).thenReturn(1L);
        when(resultSet.getString("address")).thenReturn("anyString");

        Photo photo = mapper.mapRow(resultSet);

        assertEquals(1, photo.getId());
        assertEquals("anyString", photo.getAddress());
    }
}