package com.rk.dao.jdbc.mapper;

import com.rk.dao.exception.JdbcException;
import com.rk.domain.Glasses;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Slf4j
public class GlassesRowMapper {
    public Glasses mapRow(ResultSet resultSet) {
        Glasses glasses = new Glasses();
        try {
            glasses.setId(resultSet.getLong("glasses_id"));
            glasses.setName(resultSet.getString("name"));
            glasses.setCollection(resultSet.getString("collection"));
            glasses.setCategory(resultSet.getString("category"));
            glasses.setDetails(resultSet.getString("details"));
            glasses.setPrice(resultSet.getDouble("price"));
            glasses.setPhotos(new ArrayList<>());
            return glasses;
        } catch (SQLException e) {
            log.error("Can not find field of glasses in ResultSet", e);
            throw new JdbcException("Can not find field of glasses in ResultSet", e);
        }
    }
}
