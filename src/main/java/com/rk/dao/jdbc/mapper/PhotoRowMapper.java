package com.rk.dao.jdbc.mapper;

import com.rk.domain.Photo;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class PhotoRowMapper {
    @SneakyThrows
    public Photo mapRow(ResultSet resultSet) {
        Photo photo = new Photo();
        photo.setId(resultSet.getLong("photo_id"));
        photo.setAddress(resultSet.getString("address"));
        return photo;
    }
}
