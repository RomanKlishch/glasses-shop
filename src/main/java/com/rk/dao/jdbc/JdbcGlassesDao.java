package com.rk.dao.jdbc;

import com.rk.dao.GlassesDao;
import com.rk.dao.jdbc.mapper.GlassesRowMapper;
import com.rk.dao.jdbc.mapper.PhotoRowMapper;
import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.util.PropertyReader;
import lombok.Cleanup;
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
public class JdbcGlassesDao implements GlassesDao {
    private static final GlassesRowMapper glassesRowMapper = new GlassesRowMapper(); //TODO: почему метод mapRow не статический? Отсюда соответственно вопрос правильно ли я понимаю поведение сттатических методов в многопоточности?
    private static final PhotoRowMapper photoRowMapper = new PhotoRowMapper();
    private DataSource dataSource;
    private PropertyReader propertyReader = new PropertyReader("properties/sqlQueries.properties");

    private String findAll = propertyReader.getProperties("find.all");
    private String findByName = propertyReader.getProperties("find.by.name");
    private String findByCategory = propertyReader.getProperties("find.by.category");
    private String findById = propertyReader.getProperties("find.by.id");
    private String saveGlasses = propertyReader.getProperties("save.glasses");
    private String savePhoto = propertyReader.getProperties("save.photo");
    private String updateGlasses = propertyReader.getProperties("update.glasses");
    private String updatePhoto = propertyReader.getProperties("update.photo");
    private String deleteGlasses = propertyReader.getProperties("delete.glasses");
    private String deletePhoto = propertyReader.getProperties("delete.photo");
    private String findRandomGlasses = propertyReader.getProperties("find.random.glasses");

    public JdbcGlassesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @SneakyThrows
    public List<Glasses> findAll() {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(findAll);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();

        return mapRowGlassesAndPhoto(resultSet);
    }

    @SneakyThrows
    @Override
    public List<Glasses> findListOfRandom(int limit) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(findRandomGlasses);
        statementGlasses.setInt(1, limit);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();

        return mapRowGlassesAndPhoto(resultSet);
    }

    //TODO: последовательность операций JOIN и SELECT, какая из них первая какая вторая и можна ли их менять местами?
    @Override
    @SneakyThrows
    public List<Glasses> findAllByName(String name) {
        String parameter = "%".concat(name).concat("%");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(findByName);
        statementGlasses.setString(1, parameter);
        statementGlasses.setString(2, parameter);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();

        return mapRowGlassesAndPhoto(resultSet);
    }

    @Override
    @SneakyThrows
    public Glasses findById(long id) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(findById);
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

    @SneakyThrows
    protected void savePhoto(Connection connection, List<Photo> photos, long id) {
        @Cleanup PreparedStatement statement = connection.prepareStatement(savePhoto);
        for (Photo photo : photos) {
            statement.setLong(1, id);
            statement.setString(2, photo.getAddress());
            statement.addBatch();
        }
        statement.executeBatch();
    }

    @Override
    @SneakyThrows
    public void saveGlasses(Glasses glasses) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(saveGlasses, PreparedStatement.RETURN_GENERATED_KEYS);
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

    @SneakyThrows
    private void updatePhoto(Connection connection, List<Photo> photos) {
        @Cleanup PreparedStatement statement = connection.prepareStatement(updatePhoto);
        for (Photo photo : photos) {
            statement.setString(1, photo.getAddress());
            statement.setLong(2, photo.getId());
            statement.addBatch();
        }
        statement.executeBatch();
    }

    @Override
    @SneakyThrows
    public void updateById(Glasses glasses) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(updateGlasses);
        connection.setAutoCommit(false);
        statement.setString(1, glasses.getName());
        statement.setString(2, glasses.getCollection());
        statement.setString(3, glasses.getCategory());
        statement.setString(4, glasses.getDetails());
        statement.setDouble(5, glasses.getPrice());
        statement.setLong(6, glasses.getId());
        statement.execute();
        updatePhoto(connection, glasses.getPhotos());

        connection.commit();
    }
    //TODO: у не могу удалить просто glasses, без фото. Допустим ситуация я удаляю пользователя но список его заказов должен остаться(все заказы подвязаны на user_id заказчика)
    @Override
    @SneakyThrows
    public void deleteById(long id) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(deleteGlasses);
        connection.setAutoCommit(false);
        statement.setLong(1, id);
        deletePhotoById(connection, id);
        statement.execute();
        connection.commit();
    }

    @Override
    @SneakyThrows
    public List<Glasses> findByCategory(String category) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(findByCategory);
        statementGlasses.setString(1, category);
        @Cleanup ResultSet resultSet = statementGlasses.executeQuery();

        return mapRowGlassesAndPhoto(resultSet);
    }

    @SneakyThrows
    protected void deletePhotoById(Connection connection, long id) {
        @Cleanup PreparedStatement statement = connection.prepareStatement(deletePhoto);
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