package com.rk.dao.jdbc.mapper;

import com.rk.dao.jdbc.exception.JdbcException;
import com.rk.domain.LongId;
import com.rk.domain.Order;
import com.rk.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Slf4j
public class OrderRowMapper {
    public Order mapRow(ResultSet resultSet) {
        try {
            return Order.builder()
                    .id(resultSet.getLong("order_id"))
                    .user(User.builder().id((new LongId<>(resultSet.getLong("user_id")))).build())
                    .status(resultSet.getString("status"))
                    .glassesMap(new HashMap<>()).build();
        } catch (SQLException e) {
            log.error("Can not find field of order in ResultSet", e);
            throw new JdbcException("Can not find field of order in ResultSet", e);
        }
    }
}
