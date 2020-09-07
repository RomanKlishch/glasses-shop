package com.rk.dao.jdbc;

import com.rk.dao.GlassesDao;
import com.rk.dao.jdbc.mapper.GlassesRowMapper;
import com.rk.dao.jdbc.mapper.PhotoRowMapper;
import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcGlassesDao implements GlassesDao {
    private GlassesRowMapper glassesRowMapper = new GlassesRowMapper();
    private PhotoRowMapper photoRowMapper = new PhotoRowMapper();
    private DataSource dataSource;

    private static final String FIND_ALL = "SELECT glasses_id, name, collection, category, details, price FROM glasses";
    private static final String FIND_ALL_PHOTO_BY_GLASSES_ID = "SELECT photo_id, address FROM photos WHERE glasses_id = ?";
    private static final String FIND_ALL_BY_NAME = "SELECT glasses_id, name, collection, category, details, price FROM glasses WHERE name ILIKE (?) OR collection ILIKE (?)";
    private static final String FIND_BY_ID = "SELECT glasses_id, name, collection, category, details, price FROM glasses WHERE glasses_id = ?";
    private static final String SAVE_GLASSES = "INSERT INTO glasses (name, collection, category, details, price) VALUES (?, ?, ?, ?, ?)";
    private static final String SAVE_PHOTO = "INSERT INTO photos (glasses_id, address) VALUES (?, ?)";
    private static final String UPDATE_GLASSES = "UPDATE glasses SET name=?, collection=?, category=?, details=?, price=? WHERE glasses_id=?";
    private static final String UPDATE_PHOTO = "UPDATE photos SET address=? WHERE photo_id=?";
    private static final String DELETE_GLASSES = "DELETE FROM glasses WHERE glasses_id=?";
    private static final String DELETE_PHOTO = "DELETE FROM photos WHERE glasses_id=?";
    private static final String FIND_RANDOM_GLASSES = "SELECT glasses_id, name, collection, category, details, price FROM glasses ORDER BY RANDOM() LIMIT ?";


    public JdbcGlassesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @SneakyThrows
    public List<Glasses> findAll() {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(FIND_ALL);
        @Cleanup ResultSet resultSetGlasses = statementGlasses.executeQuery();

        return getGlasses(connection, resultSetGlasses);
    }


    @SneakyThrows
    @Override
    public List<Glasses> findAll(int limit) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(FIND_RANDOM_GLASSES);
        statementGlasses.setInt(1, limit);
        @Cleanup ResultSet resultSetGlasses = statementGlasses.executeQuery();

        return getGlasses(connection, resultSetGlasses);
    }

    @Override
    @SneakyThrows
    public List<Glasses> findAllByName(String name) {
        String parameter = "%".concat(name).concat("%");
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(FIND_ALL_BY_NAME);
        statementGlasses.setString(1, parameter);
        statementGlasses.setString(2, parameter);
        @Cleanup ResultSet resultSetGlasses = statementGlasses.executeQuery();

        return getGlasses(connection, resultSetGlasses);
    }

    @Override
    @SneakyThrows
    public Glasses findById(long id) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statementGlasses = connection.prepareStatement(FIND_BY_ID);
        statementGlasses.setLong(1, id);
        @Cleanup ResultSet resultSetGlasses = statementGlasses.executeQuery();
        List<Photo> photoList = new ArrayList<>();
        Glasses glasses = new Glasses();

        while (resultSetGlasses.next()) {
            glasses = glassesRowMapper.glassesMapRow(resultSetGlasses);
            @Cleanup PreparedStatement statementPhoto = connection.prepareStatement(FIND_ALL_PHOTO_BY_GLASSES_ID);
            statementPhoto.setLong(1, glasses.getGlassesId());
            @Cleanup ResultSet resultSetPhoto = statementPhoto.executeQuery();
            while (resultSetPhoto.next()) {
                Photo photo = photoRowMapper.photoMapRow(resultSetPhoto);
                photoList.add(photo);
            }
            glasses.setPhotos(photoList);
        }
        return glasses;
    }

    @SneakyThrows
    protected void savePhoto(Connection connection, List<Photo> photos, long id) {
        @Cleanup PreparedStatement statement = connection.prepareStatement(SAVE_PHOTO);
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
        @Cleanup PreparedStatement statement = connection.prepareStatement(SAVE_GLASSES, PreparedStatement.RETURN_GENERATED_KEYS);
        connection.setAutoCommit(false);
        statement.setString(1, glasses.getName());
        statement.setString(2, glasses.getCollection());
        statement.setString(3, glasses.getCategory());
        statement.setString(4, glasses.getDetails());
        statement.setDouble(5, glasses.getPrice());
        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();
        while (resultSet.next()) {
            glasses.setGlassesId(resultSet.getLong(1));
        }
        savePhoto(connection, glasses.getPhotos(), glasses.getGlassesId());
        connection.commit();
    }


    @SneakyThrows
    private void updatePhoto(Connection connection, List<Photo> photos) {
        @Cleanup PreparedStatement statement = connection.prepareStatement(UPDATE_PHOTO);
        for (Photo photo : photos) {
            statement.setString(1, photo.getAddress());
            statement.setLong(2, photo.getPhotoId());
            statement.addBatch();
        }
        statement.executeBatch();
    }

    @Override
    @SneakyThrows
    public void updateById(Glasses glasses) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(UPDATE_GLASSES);
        connection.setAutoCommit(false);
        statement.setString(1, glasses.getName());
        statement.setString(2, glasses.getCollection());
        statement.setString(3, glasses.getCategory());
        statement.setString(4, glasses.getDetails());
        statement.setDouble(5, glasses.getPrice());
        statement.setLong(6, glasses.getGlassesId());
        statement.execute();
        updatePhoto(connection, glasses.getPhotos());

        connection.commit();
    }

    @Override
    @SneakyThrows
    public void deleteById(long id) {
        @Cleanup Connection connection = dataSource.getConnection();
        @Cleanup PreparedStatement statement = connection.prepareStatement(DELETE_GLASSES);
        connection.setAutoCommit(false);
        statement.setLong(1, id);
        deletePhotoById(connection, id);
        statement.execute();
        connection.commit();
    }

    @SneakyThrows
    protected void deletePhotoById(Connection connection, long id) {
        @Cleanup PreparedStatement statement = connection.prepareStatement(DELETE_PHOTO);
        statement.setLong(1, id);
        statement.execute();
    }

    private List<Glasses> getGlasses(Connection connection, ResultSet resultSetGlasses) throws SQLException {
        List<Glasses> glassesList = new ArrayList<>();
        List<Photo> photosList;

        while (resultSetGlasses.next()) {
            Glasses glasses = glassesRowMapper.glassesMapRow(resultSetGlasses);
            @Cleanup PreparedStatement statementPhoto = connection.prepareStatement(FIND_ALL_PHOTO_BY_GLASSES_ID);
            statementPhoto.setLong(1, glasses.getGlassesId());
            @Cleanup ResultSet resultSetPhoto = statementPhoto.executeQuery();
            photosList = new ArrayList<>();
            while (resultSetPhoto.next()) {
                Photo photo = photoRowMapper.photoMapRow(resultSetPhoto);
                photosList.add(photo);
            }
            glasses.setPhotos(photosList);
            glassesList.add(glasses);
        }
        return glassesList;
    }
}
//TODO: проблема с Statement.RETURN_GENERATED_KEYS(), как ее решают на реальных проектах или как бы ее решал ты?
// ("INSERT INTO post (title) VALUES (?) RETURNING id" - это частный случай для postgres)
//TODO: Need I connection.commit() after query("SELECT....")?(I think no)