package com.rk.dao.jdbc;

import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.util.PropertyReader;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JdbcGlassesDaoITest {
    private final Flyway flyway;
    private final JdbcGlassesDao jdbcGlassesDao;
    private PropertyReader propertyReader = new PropertyReader();

    public JdbcGlassesDaoITest() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;");
        FluentConfiguration configuration = new FluentConfiguration();
        configuration.locations("db/migration/glasses");
        configuration.dataSource(jdbcDataSource);
        flyway = new Flyway(configuration);
        jdbcGlassesDao = new JdbcGlassesDao(jdbcDataSource,propertyReader);
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
    @DisplayName("Find random glasses")
    void findAllLimit() {
        List<Glasses> glassesList = jdbcGlassesDao.findListOfRandom(1);
        assertEquals(1, glassesList.size());
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
        Photo photo = Photo.builder().address("test-first").build();
        Photo photo2 = Photo.builder().address("test-second").build();
        Glasses expected = Glasses.builder()
                .name("TEST")
                .collection("TEST")
                .category("TEST")
                .details("TEST")
                .price(5)
                .photos(Arrays.asList(photo,photo2)).build();

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
        Photo photo = Photo.builder().address("test-first").build();
        Photo photo2 = Photo.builder().address("test-second").build();
        Glasses expected = Glasses.builder().id(2L)
                .name("TEST")
                .collection("TEST")
                .category("TEST")
                .details("TEST")
                .price(5)
                .photos(Arrays.asList(photo,photo2)).build();

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

    @Test
    @DisplayName("Find by category")
    void findByCategory() {
        List<Glasses> glassesList = jdbcGlassesDao.findByCategory("sun");

        assertEquals(1, glassesList.size());
        assertEquals(1, glassesList.get(0).getId());
        assertEquals("bas", glassesList.get(0).getName());
    }


    private Glasses getGlasses() {
        Photo photo = Photo.builder().address("test-first").build();
        Photo photo2 = Photo.builder().address("test-second").build();

        return Glasses.builder()
                .name("TEST")
                .collection("TEST")
                .category("TEST")
                .details("TEST")
                .price(5)
                .photos(Arrays.asList(photo,photo2)).build();
    }
}