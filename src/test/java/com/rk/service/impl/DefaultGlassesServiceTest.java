package com.rk.service.impl;

import com.rk.dao.GlassesDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultGlassesServiceTest {
    @Mock
    GlassesDao glassesDao;
    @InjectMocks
    DefaultGlassesService glassesService;

    @Test
    void findById() {
        when(glassesDao.findById(anyLong())).thenReturn(null);
        glassesService.findById(1L);
        verify(glassesDao, atLeast(1)).findById(1L);
    }

    @Test
    void getListsFeaturesAndSpecialGlasses() {
        when(glassesDao.findListOfRandom(anyInt())).thenReturn(null);
        glassesService.getListsFeaturesAndSpecialGlasses(1, 2);
        verify(glassesDao, atLeast(1)).findListOfRandom(1);
        verify(glassesDao, atLeast(1)).findListOfRandom(2);
    }

    @Test
    void getCatalogAndMessage_SUN() {
        when(glassesDao.findByCategory("SUN")).thenReturn(null);
        glassesService.getCategoryList("SUN");
        verify(glassesDao, atLeast(1)).findByCategory("SUN");
    }

    @Test
    void getCatalogAndMessage_OPTICAL() {
        when(glassesDao.findByCategory("OPTICAL")).thenReturn(null);
        glassesService.getCategoryList("OPTICAL");
        verify(glassesDao, atLeast(1)).findByCategory("OPTICAL");

    }

    @Test
    void getCategoryList() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }
}