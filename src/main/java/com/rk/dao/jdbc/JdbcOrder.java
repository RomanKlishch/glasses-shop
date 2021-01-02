package com.rk.dao.jdbc;

import com.rk.dao.OrderDao;
import com.rk.dao.jdbc.exception.JdbcException;
import com.rk.dao.jdbc.mapper.OrderRowMapper;
import com.rk.domain.Glasses;
import com.rk.domain.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class JdbcOrder implements OrderDao {
    private static final OrderRowMapper ORDER_ROW_MAPPER = new OrderRowMapper();
    private String findAllOrders = "SELECT orders.order_id, user_id, status, orders_glasses.count, glasses.glasses_id, glasses.name,glasses.collection,glasses.price FROM orders LEFT JOIN orders_glasses ON orders.order_id=orders_glasses.order_id LEFT JOIN glasses ON orders_glasses.glasses_id=glasses.glasses_id";
    private String findAllOrdersByUserId = "SELECT orders.order_id, user_id, status, orders_glasses.count, glasses.glasses_id, glasses.name,glasses.collection,glasses.price FROM orders LEFT JOIN orders_glasses ON orders.order_id=orders_glasses.order_id LEFT JOIN glasses ON orders_glasses.glasses_id=glasses.glasses_id WHERE user_id=?";
    private String saveOrder = "INSERT INTO orders (user_id, status) VALUES (?, ?);";
    private String saveGlassesOrder = "INSERT INTO orders_glasses (order_id, glasses_id, count) VALUES (?, ?, ?);";

    private DataSource dataSource;

    public JdbcOrder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Order> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(findAllOrders)) {
            return mapRowOrderAndGlasses(resultSet);
        } catch (SQLException e) {
            log.error("Can not find all orders - {}", e);
            throw new JdbcException("Can not find all orders", e);
        }
    }

    @Override
    public List<Order> findByUserId(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllOrdersByUserId)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return mapRowOrderAndGlasses(resultSet);
            }

        } catch (SQLException e) {
            log.error("Can not find all orders by user_id - {}", e);
            throw new JdbcException("Can not find all orders  by user_id", e);
        }
    }

    @Override
    public void save(Order order) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(saveOrder, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setLong(1, order.getUser().getId().getId());
            statement.setString(2, order.getStatus());
            statement.execute();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    order.setId(resultSet.getLong(1));
                }
            }

            saveGlassesOrder(connection, order);
            connection.commit();
        } catch (SQLException e) {
            log.error("Can not save order - {}", order, e);
            throw new JdbcException("Can not save order", e);
        }
    }

    private void saveGlassesOrder(Connection connection, Order order) {
        long orderId = order.getId();
        try (PreparedStatement statement = connection.prepareStatement(saveGlassesOrder)) {
            Map<Glasses, Integer> glassesMap = order.getGlassesMap();
            for (Glasses glasses : glassesMap.keySet()) {
                statement.setLong(1, orderId);
                statement.setLong(2, glasses.getId());
                statement.setInt(3, glassesMap.get(glasses));
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            log.error("Can not save order in table glasses_order - {}", order, e);
        }
    }

    private List<Order> mapRowOrderAndGlasses(ResultSet resultSet) throws SQLException {
        Map<Long, Order> orderMap = new HashMap<>();
        Order order = null;
        while (resultSet.next()) {
            long id = resultSet.getLong("order_id");
            if (!orderMap.containsKey(id)) {
                order = ORDER_ROW_MAPPER.mapRow(resultSet);
                orderMap.put(id, order);
            }
            Glasses glasses = Glasses.builder()
                    .id(resultSet.getLong("glasses_id"))
                    .name(resultSet.getString("name"))
                    .collection(resultSet.getString("collection"))
                    .price(resultSet.getDouble("price")).build();
            int count = resultSet.getInt("count");
            order.getGlassesMap().put(glasses, count);

        }
        return new ArrayList<>(orderMap.values());
    }
}
