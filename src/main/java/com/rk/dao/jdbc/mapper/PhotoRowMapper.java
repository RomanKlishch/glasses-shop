package com.rk.dao.jdbc.mapper;

import com.rk.domain.Photo;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class PhotoRowMapper {
    @SneakyThrows
    public Photo photoMapRow(ResultSet resultSet){
        Photo photo = new Photo();
        photo.setPhotoId(resultSet.getLong("photo_id"));
        photo.setAddress(resultSet.getString("address"));
        return photo;
    }
}
