package com.rk.dao.jdbc;

import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JdbcGlassesDaoITest {
    Flyway flyway;
    JdbcDataSource jdbcDataSource;
    JdbcGlassesDao jdbcGlassesDao;

    public JdbcGlassesDaoITest() {
        jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;");
        FluentConfiguration configuration = new FluentConfiguration();
        configuration.locations("db/migration");
        configuration.dataSource(jdbcDataSource);
        flyway = new Flyway(configuration);
        jdbcGlassesDao = new JdbcGlassesDao(jdbcDataSource);
    }

    @BeforeEach
    void init() {
        flyway.migrate();
    }

    @AfterEach
    void afterAll() {
        flyway.clean();
    }

    @Test
    @DisplayName("Find all glasses")
    void testFindAll() {
        List<Glasses> glassesList = jdbcGlassesDao.findAll();
        assertEquals(3, glassesList.size());
        assertEquals(2, glassesList.get(1).getPhotos().size());
        assertEquals(0, glassesList.get(2).getPhotos().size());
    }

    @Test
    @DisplayName("Find all glasses by part of name")
    void testFindAllByPartName() {
        List<Glasses> glassesList = jdbcGlassesDao.findAllByName("ups");
        assertEquals(1, glassesList.size());
    }

    @Test
    @DisplayName("Find all glasses by part of collection")
    void testFindAllByPartCollection() {
        List<Glasses> glassesList = jdbcGlassesDao.findAllByName("fox");
        assertEquals(2, glassesList.size());

    }

    @Test
    @DisplayName("Find all glasses")
    void testFindById() {
        List<Photo> photoList = new ArrayList<>();
        Photo photo1 = new Photo(1, "first-first");
        Photo photo2 = new Photo(2, "first-second");
        photoList.add(photo1);
        photoList.add(photo2);

        Glasses actualGlasses = new Glasses(1, "bas", "fox", "sun", "good-good", 25.25, photoList);
        Glasses expectedGlasses = jdbcGlassesDao.findById(1);

        assertEquals(actualGlasses, expectedGlasses);
    }

    @Test
    @DisplayName("Save glasses")
    void testSaveGlasses() {
        Glasses expected = getGlasses();

        jdbcGlassesDao.saveGlasses(expected);

        assertEquals(expected, jdbcGlassesDao.findById(4));
        assertEquals(4, jdbcGlassesDao.findAll().size());
    }

    //TODO: write implementation test. Do not save glasses if photo save fails
    @DisplayName("Do not save glasses")
    void testNotSaveGlasses() {
        Glasses expected = getGlasses();

        jdbcGlassesDao.saveGlasses(expected);

        assertNull(jdbcGlassesDao.findById(4));
        assertEquals(3, jdbcGlassesDao.findAll().size());
    }

    @Test
    @DisplayName("Update glasses by id")
    void testUpdateById() {
        Glasses expected = getGlasses();

        jdbcGlassesDao.updateById(expected);
        Glasses actual = jdbcGlassesDao.findById(2);

        assertEquals(expected, actual);
        assertEquals(3, jdbcGlassesDao.findAll().size());
    }

    @Test
    @DisplayName("Delete glasses by id")
    void testDeleteById() {
        jdbcGlassesDao.deleteById(3);
        List<Glasses> glassesList = jdbcGlassesDao.findAll();

        assertEquals(2, glassesList.size());
        assertEquals("bas", glassesList.get(0).getName());
        assertEquals("pol", glassesList.get(1).getName());
    }

    private Glasses getGlasses() {
        List<Photo> photoList = new ArrayList<>();
        Photo photo1 = new Photo();
        Photo photo2 = new Photo();
        photo1.setAddress("test-first");
        photo2.setAddress("test-second");
        photoList.add(photo1);
        photoList.add(photo2);
        Glasses expected = new Glasses();
        expected.setGlassesId(2L);
        expected.setName("TEST");
        expected.setCollection("TEST");
        expected.setCategory("TEST");
        expected.setDetails("TEST");
        expected.setPrice(1.0);
        expected.setPhotos(photoList);
        return expected;
    }
}