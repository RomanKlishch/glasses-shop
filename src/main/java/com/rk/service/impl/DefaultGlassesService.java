package com.rk.service.impl;

import com.rk.dao.GlassesDao;
import com.rk.domain.Glasses;
import com.rk.dto.CatalogAndMessage;
import com.rk.dto.FeaturesAndSpecialGlasses;
import com.rk.service.GlassesService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DefaultGlassesService implements GlassesService {
    private GlassesDao glassesDao;

    public DefaultGlassesService(GlassesDao glassesDao) {
        this.glassesDao = glassesDao;
    }

    @Override
    public Glasses findById(long id) {
        return glassesDao.findById(id);
    }

    @Override
    public List<Glasses> findAll() {
        return glassesDao.findAll();
    }

    @Override
    public List<Glasses> findByName(String name) {
        return glassesDao.findAllByName(name);
    }

    @Override
    public FeaturesAndSpecialGlasses getListsFeaturesAndSpecialGlasses(int limitFeature, int limitSpecial) {
        List<Glasses> featureList = glassesDao.findListOfRandom(limitFeature);
        List<Glasses> specialList = glassesDao.findListOfRandom(limitSpecial);

        return FeaturesAndSpecialGlasses.builder().feature(featureList).special(specialList).build();
    }

    @Override
    public CatalogAndMessage getCatalogAndMessage(HttpServletRequest request) {
        String name = request.getParameter("searchName");
        if (name != null) {
            return CatalogAndMessage.builder().catalog(glassesDao.findAllByName(name)).message("Found by your request").build();
        } else {
            return CatalogAndMessage.builder().catalog(glassesDao.findAll()).message("Catalog of glasses").build();
        }
    }

    @Override
    public List<Glasses> getCategoryList(String category) {
        List<Glasses> glassesList = glassesDao.findByCategory(category);
        return glassesList;
    }
}

