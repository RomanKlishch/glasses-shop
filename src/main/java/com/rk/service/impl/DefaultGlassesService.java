package com.rk.service.impl;

import com.rk.dao.GlassesDao;
import com.rk.domain.Glasses;
import com.rk.service.GlassesService;

import java.util.List;

public class DefaultGlassesService implements GlassesService {
    GlassesDao glassesDao;

    public DefaultGlassesService(GlassesDao glassesDao) {
        this.glassesDao = glassesDao;
    }

    @Override
    public List<Glasses> findRandomGlasses(int limit) {
        return glassesDao.findAll(limit);
    }
}

