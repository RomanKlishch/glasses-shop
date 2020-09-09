package com.rk.dao.jdbc.mapper;

import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GlassesRowMapper {
    @SneakyThrows
    public Glasses mapRow(ResultSet resultSet) {
        Glasses glasses = new Glasses();
        glasses.setId(resultSet.getLong("glasses_id"));
        glasses.setName(resultSet.getString("name"));
        glasses.setCollection(resultSet.getString("collection"));
        glasses.setCategory(resultSet.getString("category"));
        glasses.setDetails(resultSet.getString("details"));
        glasses.setPrice(resultSet.getDouble("price"));
        glasses.setPhotos(new ArrayList<>());
        return glasses;
    }
}
