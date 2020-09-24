package com.rk.dao.jdbc;

import com.rk.dao.UserDao;
import com.rk.dao.exception.JdbcException;
import com.rk.dao.jdbc.mapper.UserRowMapper;
import com.rk.domain.User;
import com.rk.util.PropertyReader;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper userRowMapper = new UserRowMapper();
    private DataSource dataSource;
    private PropertyReader propertyReader;

    public JdbcUserDao(DataSource dataSource, PropertyReader propertyReader) {
        this.dataSource = dataSource;
        this.propertyReader = propertyReader;
    }

    @Override
    public User findById(long id) {
        String query = propertyReader.getProperty("find.by.id.user");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                User user = null;
                while (resultSet.next()) {
                    user = userRowMapper.mapRow(resultSet);
                    if (resultSet.next()) {
                        throw new JdbcException("More then one user found");
                    }
                }
                return user;
            }
        } catch (SQLException e) {
            log.error("Find by id user", e);
            throw new JdbcException("Find by id user", e);
        }
    }

    @Override
    public List<User> findAll() {
        String query = propertyReader.getProperty("find.all.user");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = userRowMapper.mapRow(resultSet);
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            log.error("Find list of user", e);
            throw new JdbcException("Find list of user", e);
        }
    }

    @Override
    public void save(User user) {
        String query = propertyReader.getProperty("save.user");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,user.getName());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getPassword());
            statement.setString(4, user.getRole().getUserRole());
            statement.execute();
        } catch (SQLException e) {
            log.error("Save user", e);
            throw new JdbcException("Save user", e);
        }
    }

    @Override
    public void update(User user) {
        String query = propertyReader.getProperty("update.user");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,user.getName());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getPassword());
            statement.setString(4, user.getRole().getUserRole());
            statement.setLong(4, user.getId().getId());
            statement.execute();
        } catch (SQLException e) {
            log.error("Update user", e);
            throw new JdbcException("Update user", e);
        }
    }

    @Override
    public void delete(long id) {
        String query = propertyReader.getProperty("delete.user");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(4, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Delete user", e);
            throw new JdbcException("Delete user", e);
        }
    }
}
