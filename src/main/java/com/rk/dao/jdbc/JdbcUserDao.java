package com.rk.dao.jdbc;

import com.rk.dao.UserDao;
import com.rk.dao.jdbc.exception.JdbcException;
import com.rk.dao.jdbc.mapper.UserRowMapper;
import com.rk.domain.User;
import com.rk.domain.UserRole;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper userRowMapper = new UserRowMapper();

    private String findByIdUser = "SELECT user_id, name, email, password, sole, role FROM users where user_id=?;";
    private String findAllUser = "SELECT user_id, name, email, password, sole, role FROM users;";
    private String saveUser = "INSERT INTO users (name, email, password, sole, role) VALUES (?, ?, ?, ?, ?);";
    private String updateUser = "UPDATE users SET name=?, email=?, password=?, role=? WHERE user_id=?;";
    private String deleteUser = "DELETE FROM users WHERE user_id=?;";
    private String findByLogin = "SELECT user_id, name, email, password, sole, role FROM users WHERE email =?;";

    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdUser)) {
            statement.setLong(1, id);

            return getUser(statement);

        } catch (SQLException e) {
            log.error("Find by id user", e);
            throw new JdbcException("Find by id user", e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(findAllUser)) {

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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(saveUser)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getSole());
            UserRole saveRole = user.getRole() == null ? UserRole.USER : user.getRole();
            statement.setString(5, saveRole.getNameOfUserRole());
            statement.execute();

        } catch (SQLException e) {
            log.error("Save user - {}", user, e);
            throw new JdbcException("Save user", e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateUser)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().getNameOfUserRole());
            statement.setLong(5, user.getId().getId());
            statement.execute();

        } catch (SQLException e) {
            log.error("Update user - {}", user, e);
            throw new JdbcException("Update user", e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteUser)) {

            statement.setLong(1, id);
            statement.execute();

        } catch (SQLException e) {
            log.error("Delete user by id - {}", id, e);
            throw new JdbcException("Delete user", e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByLogin)) {

            statement.setString(1, login);
            return getUser(statement);

        } catch (SQLException e) {
            log.error("Find by login user", e);
            throw new JdbcException("Find by login user", e);
        }
    }

    private User getUser(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = userRowMapper.mapRow(resultSet);
                if (resultSet.next()) {
                    throw new JdbcException("More then one user found");
                }
                return user;
            }
            return null;
        }
    }
}
