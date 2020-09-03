package com.rk.dao.mapper;

import com.rk.domain.Glasses;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class GlassesRowMapper {
    @SneakyThrows
    public Glasses glassesMapRow(ResultSet resultSet) {
        Glasses glasses = new Glasses();
        glasses.setGlassesId(resultSet.getLong("glasses_id"));
        glasses.setName(resultSet.getString("name"));
        glasses.setCollection(resultSet.getString("collection"));
        glasses.setCategory(resultSet.getString("category"));
        glasses.setDetails(resultSet.getString("details"));
        return glasses;
    }
}
