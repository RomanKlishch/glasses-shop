package com.rk.dao.jdbc.mapper;

import com.rk.dao.jdbc.exception.JdbcException;
import com.rk.domain.Photo;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class PhotoRowMapper {
    public Photo mapRow(ResultSet resultSet) {
        Photo photo = new Photo();
        try {
            photo.setId(resultSet.getLong("photo_id"));
            photo.setPathToImage(resultSet.getString("path_to_image"));
            return photo;
        } catch (SQLException e) {
            log.error("Can not find field of photo in ResultSet", e);
            throw new JdbcException("Can not find field of photo in ResultSet", e);
        }
    }
}
