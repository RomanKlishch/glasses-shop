package com.rk.dao.jdbc.mapper;

import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class UserRowMapper {

    @SneakyThrows
    public User rowMap(ResultSet resultSet) {
        return User.builder()
                .id(new LongId<>(resultSet.getLong("user_id")))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .role(UserRole.valueOf(resultSet.getString("password")))
                .build();
    }
}
