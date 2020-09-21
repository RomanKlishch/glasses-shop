package com.rk.dao.jdbc;

import com.rk.dao.GlassesDao;
import com.rk.dao.jdbc.mapper.GlassesRowMapper;
import com.rk.dao.jdbc.mapper.PhotoRowMapper;
import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.util.PropertyReader;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor
public class JdbcGlassesDao implements GlassesDao {
    private static final GlassesRowMapper glassesRowMapper = new GlassesRowMapper(); //TODO: почему метод mapRow не статический? Отсюда соответственно вопрос правильно ли я понимаю поведение сттатических методов в многопоточности?
    private static final PhotoRowMapper photoRowMapper = new PhotoRowMapper();
    private DataSource dataSource;
    private PropertyReader propertyReader;

    public JdbcGlassesDao(DataSource dataSource, PropertyReader propertyReader) {
        this.dataSource = dataSource;
        this.propertyReader = propertyReader;
    }

    @Override
    @SneakyThrows
    public List<Glasses> findAll() {
        String query = propertyReader.getProperty("find.all");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(query);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();

        return mapRowGlassesAndPhoto(resultSet);
    }

    @SneakyThrows
    @Override
    public List<Glasses> findListOfRandom(int limit) {
        String query = propertyReader.getProperty("find.random.glasses");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(query);
        statementGlasses.setInt(1, limit);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();

        return mapRowGlassesAndPhoto(resultSet);
    }

    @Override
    @SneakyThrows
    public List<Glasses> findAllByName(String name) {
        String query = propertyReader.getProperty("find.by.name");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(query);
        statementGlasses.setString(1, name);
        statementGlasses.setString(2, name);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();

        return mapRowGlassesAndPhoto(resultSet);
    }

    @Override
    @SneakyThrows
    public Glasses findById(long id) {
        String query = propertyReader.getProperty("find.by.id");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(query);
        statementGlasses.setLong(1, id);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();
        Glasses glasses = null;

        while (resultSet.next()) {
            if (glasses == null) {
                glasses = glassesRowMapper.mapRow(resultSet);
            }
            Photo photo = photoRowMapper.mapRow(resultSet);
            glasses.getPhotos().add(photo);
        }
        return glasses;
    }

    @Override
    @SneakyThrows
    public void saveGlasses(Glasses glasses) {
        String query = propertyReader.getProperty("save.glasses");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        connection.setAutoCommit(false);
        statement.setString(1, glasses.getName());
        statement.setString(2, glasses.getCollection());
        statement.setString(3, glasses.getCategory());
        statement.setString(4, glasses.getDetails());
        statement.setDouble(5, glasses.getPrice());
        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();
        while (resultSet.next()) {
            glasses.setId(resultSet.getLong(1));
        }
        savePhoto(connection, glasses.getPhotos(), glasses.getId());
        connection.commit();
    }

    //TODO:JdbcBatchUpdateException: Нарушение ссылочной целостности: "CONSTRAINT_8C: PUBLIC.PHOTOS FOREIGN KEY(GLASSES_ID) REFERENCES PUBLIC.GLASSES(GLASSES_ID) (4)
    @SneakyThrows
    protected void savePhoto(Connection connection, List<Photo> photos, long id) {
        String query = propertyReader.getProperty("save.photo");
        @Cleanup PreparedStatement statement = connection.prepareStatement(query);
        for (Photo photo : photos) {
            statement.setLong(1, id);
            statement.setString(2, photo.getAddress());
            statement.addBatch();
        }
        statement.executeBatch();
    }

    @Override
    @SneakyThrows
    public void updateById(Glasses glasses) {
        String query = propertyReader.getProperty("update.glasses");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(query);
        connection.setAutoCommit(false);
        statement.setString(1, glasses.getName());
        statement.setString(2, glasses.getCollection());
        statement.setString(3, glasses.getCategory());
        statement.setString(4, glasses.getDetails());
        statement.setDouble(5, glasses.getPrice());
        statement.setLong(6, glasses.getId());
        statement.execute();
        updatePhoto(glasses.getPhotos());

        connection.commit();
    }

    @SneakyThrows
    private void updatePhoto(List<Photo> photos) {
        String query = propertyReader.getProperty("update.photo");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(query);
        for (Photo photo : photos) {
            statement.setString(1, photo.getAddress());
            statement.setLong(2, photo.getId());
            statement.addBatch();
        }
        statement.executeBatch();
    }

    //TODO: у не могу удалить просто glasses, без фото. Допустим ситуация я удаляю пользователя но список его заказов должен остаться(все заказы подвязаны на user_id заказчика)
    @Override
    @SneakyThrows
    public void deleteById(long id) {
        String query = propertyReader.getProperty("delete.glasses");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(query);
        connection.setAutoCommit(false);
        statement.setLong(1, id);
        deletePhotoById(connection, id);
        statement.execute();
        connection.commit();
    }

    @Override
    @SneakyThrows
    public List<Glasses> findByCategory(String category) {
        String query = propertyReader.getProperty("find.by.category");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(query);
        statementGlasses.setString(1, category);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();

        return mapRowGlassesAndPhoto(resultSet);
    }

    @SneakyThrows
    protected void deletePhotoById(Connection connection, long id) {
        String query = propertyReader.getProperty("delete.photo");
        @Cleanup PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.execute();
    }

    private List<Glasses> mapRowGlassesAndPhoto(ResultSet resultSet) throws SQLException {
        Map<Long, Glasses> glassesMap = new HashMap<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("glasses_id");
            glassesMap.putIfAbsent(id, glassesRowMapper.mapRow(resultSet));
            resultSet.getLong("photo_id");
            if (resultSet.getLong("photo_id") != 0) {
                Photo photo = photoRowMapper.mapRow(resultSet);
                glassesMap.get(id).getPhotos().add(photo);
            }
        }
        return new ArrayList<>(glassesMap.values());
    }
}