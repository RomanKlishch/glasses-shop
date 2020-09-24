package com.rk.dao.jdbc.mapper;

import com.rk.dao.exception.JdbcException;
import com.rk.domain.LongId;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class UserRowMapper {
    public User mapRow(ResultSet resultSet) {
        try {
            return User.builder()
                    .id(new LongId<>(resultSet.getLong("user_id")))
                    .name(resultSet.getString("name"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .role(UserRole.valueOf(resultSet.getString("password")))
                    .build();
        } catch (SQLException e) {
            log.error("Can not find field of user in ResultSet", e);
            throw new JdbcException("Can not find field of user in ResultSet", e);
        }
    }
}
