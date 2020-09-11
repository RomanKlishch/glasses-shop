package com.rk.service.impl;

import com.rk.dao.GlassesDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultGlassesServiceTest {
    @Mock
    GlassesDao glassesDao;
    @InjectMocks
    DefaultGlassesService service;

    @Test
    void findById() {
        when(glassesDao.findById(anyLong())).thenReturn(any());
        service.findById(2L);
        verify(glassesDao, atLeastOnce()).findById(2L);
    }

    @Test
    void getListsFeaturesAndSpecialGlasses() {
        when(glassesDao.findListOfRandom(anyInt())).thenReturn(anyList());
        service.getListsFeaturesAndSpecialGlasses(2,3);
        verify(glassesDao, atLeast(1)).findListOfRandom(2);
        verify(glassesDao, atLeast(1)).findListOfRandom(3);
    }
}