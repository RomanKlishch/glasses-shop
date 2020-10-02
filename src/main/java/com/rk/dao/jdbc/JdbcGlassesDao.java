package com.rk.dao.jdbc;

import com.rk.dao.GlassesDao;
import com.rk.dao.exception.JdbcException;
import com.rk.dao.jdbc.mapper.GlassesRowMapper;
import com.rk.dao.jdbc.mapper.PhotoRowMapper;
import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.util.PropertyReader;
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
public class JdbcGlassesDao implements GlassesDao {
    private static final GlassesRowMapper GLASSES_ROW_MAPPER = new GlassesRowMapper();
    private static final PhotoRowMapper PHOTO_ROW_MAPPER = new PhotoRowMapper();
    private DataSource dataSource;
    private PropertyReader propertyReader;

    public JdbcGlassesDao(DataSource dataSource, PropertyReader propertyReader) {
        this.dataSource = dataSource;
        this.propertyReader = propertyReader;
    }

    @Override
    public List<Glasses> findAll() {
        String query = propertyReader.getProperty("find.all");
        try (Connection connection = dataSource.getConnection();
             Statement statementGlasses = connection.createStatement();
             ResultSet resultSet = statementGlasses.executeQuery(query)) {
            return mapRowGlassesAndPhoto(resultSet);
        } catch (SQLException e) {
            log.error("Can not find all Glasses - ", e);
            throw new JdbcException("Can not find all Glasses", e);
        }
    }

    @Override
    public List<Glasses> findListOfRandom(int limit) {
        String query = propertyReader.getProperty("find.random.glasses");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statementGlasses = connection.prepareStatement(query)) {
            statementGlasses.setInt(1, limit);
            try (ResultSet resultSet = statementGlasses.executeQuery()) {
                return mapRowGlassesAndPhoto(resultSet);
            }
        } catch (SQLException e) {
            log.error("Can not find {} random Glasses - ", limit, e);
            throw new JdbcException("Can not find random Glasses", e);
        }
    }

    @Override
    public List<Glasses> findAllByName(String name) {
        String query = propertyReader.getProperty("find.by.name");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statementGlasses = connection.prepareStatement(query)) {
            statementGlasses.setString(1, name);
            statementGlasses.setString(2, name);
            try (ResultSet resultSet = statementGlasses.executeQuery()) {
                return mapRowGlassesAndPhoto(resultSet);
            }
        } catch (SQLException e) {
            log.error("Can not find Glasses with name - {}", name, e);
            throw new JdbcException("Can not find Glasses", e);
        }
    }

    @Override
    public Glasses findById(long id) {
        String query = propertyReader.getProperty("find.by.id");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                Glasses glasses = null;
                while (resultSet.next()) {
                    List<Glasses> glassesList = mapRowGlassesAndPhoto(resultSet);
                    glasses = glassesList.get(0);
                    if (glassesList.size() > 1) {
                        throw new JdbcException("More then one glasses found");
                    }
                }
                return glasses;
            }
        } catch (SQLException e) {
            log.error("Can not find glasses by id - {}", id, e);
            throw new JdbcException("Can not find glasses by id", e);
        }
    }

    @Override
    public void saveGlasses(Glasses glasses) {
        String query = propertyReader.getProperty("save.glasses");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, glasses.getName());
            statement.setString(2, glasses.getCollection());
            statement.setString(3, glasses.getCategory());
            statement.setString(4, glasses.getDetails());
            statement.setDouble(5, glasses.getPrice());
            statement.execute();

            try (ResultSet resultSet = statement.getGeneratedKeys()){
                while (resultSet.next()) {
                    glasses.setId(resultSet.getLong(1));
                }
            }

            savePhoto(connection, glasses.getPhotos(), glasses.getId());
            connection.commit();
        } catch (SQLException e) {
            log.error("Can not save glasses - {}", glasses, e);
            throw new JdbcException("Can not save glasses", e);
        }
    }

    @Override
    public void updateById(Glasses glasses) {
        String query = propertyReader.getProperty("update.glasses");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            statement.setLong(6, glasses.getId());
            statement.setString(1, glasses.getName());
            statement.setString(2, glasses.getCollection());
            statement.setString(3, glasses.getCategory());
            statement.setString(4, glasses.getDetails());
            statement.setDouble(5, glasses.getPrice());
            statement.execute();
            updatePhoto(glasses.getPhotos());

            connection.commit();
        } catch (SQLException e) {
            log.error("Can not update glasses - {}", glasses, e);
            throw new JdbcException("Can not update glasses", e);
        }

    }

    @Override
    public void deleteById(long id) {
        String query = propertyReader.getProperty("delete.glasses");

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                deletePhotoByGlassesId(connection, id);
                statement.setLong(1, id);
                statement.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Can not delete glasses", e);
                throw new JdbcException("Can not delete glasses", e);
            }
        } catch (SQLException e) {
            log.error("Connection or rollback was not executed during deletion Glasses", e);
            throw new JdbcException("Connection or rollback was not executed during deletion Glasses", e);
        }
    }

    @Override
    public List<Glasses> findByCategory(String category) {
        String query = propertyReader.getProperty("find.by.category");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statementGlasses = connection.prepareStatement(query)) {
            statementGlasses.setString(1, category);
            try (ResultSet resultSet = statementGlasses.executeQuery()) {
                return mapRowGlassesAndPhoto(resultSet);
            }
        } catch (SQLException e) {
            log.error("Can not find by category - {}", category, e);
            throw new JdbcException("Can not find by category", e);
        }
    }

    //TODO:JdbcBatchUpdateException: Нарушение ссылочной целостности: "CONSTRAINT_8C: PUBLIC.PHOTOS FOREIGN KEY(GLASSES_ID) REFERENCES PUBLIC.GLASSES(GLASSES_ID) (4)
    void savePhoto(Connection connection, List<Photo> photos, long id) {
        String query = propertyReader.getProperty("save.photo");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Photo photo : photos) {
                statement.setLong(1, id);
                statement.setString(2, photo.getPathToImage());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("Can not save photo for glasses", e);
            throw new JdbcException("Can not save photo for glasses", e);
        }
    }

    void updatePhoto(List<Photo> photos) {
        String query = propertyReader.getProperty("update.photo");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (Photo photo : photos) {
                statement.setString(1, photo.getPathToImage());
                statement.setLong(2, photo.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("Can not update photo for glasses", e);
            throw new JdbcException("Can not update photo for glasses", e);
        }
    }

    void deletePhotoByGlassesId(Connection connection, long id) {
        String query = propertyReader.getProperty("delete.photo");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error("Can not delete photo for glasses", e);
            throw new JdbcException("Can not delete photo for glasses", e);
        }
    }

    List<Glasses> mapRowGlassesAndPhoto(ResultSet resultSet) throws SQLException {
        Map<Long, Glasses> glassesMap = new HashMap<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("glasses_id");
            glassesMap.putIfAbsent(id, GLASSES_ROW_MAPPER.mapRow(resultSet));

            if (resultSet.getLong("photo_id") != 0) {
                Photo photo = PHOTO_ROW_MAPPER.mapRow(resultSet);
                glassesMap.get(id).getPhotos().add(photo);
            }
        }
        return new ArrayList<>(glassesMap.values());
    }
}