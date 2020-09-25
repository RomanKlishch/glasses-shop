package com.rk.service.impl;

import com.rk.ServiceLocator;
import com.rk.dao.GlassesDao;
import com.rk.domain.Glasses;
import com.rk.dto.CatalogAndMessage;
import com.rk.dto.FeaturesAndSpecialGlasses;
import com.rk.service.GlassesService;

import java.util.List;

public class DefaultGlassesService implements GlassesService {
    private GlassesDao glassesDao;

    public DefaultGlassesService() {
        glassesDao = ServiceLocator.getBean(GlassesDao.class);
    }

    @Override
    public Glasses findById(long id) {
        return glassesDao.findById(id);
    }

    @Override
    public FeaturesAndSpecialGlasses getListsFeaturesAndSpecialGlasses(int limitFeature, int limitSpecial) {
        List<Glasses> featureList = glassesDao.findListOfRandom(limitFeature);
        List<Glasses> specialList = glassesDao.findListOfRandom(limitSpecial);

        return FeaturesAndSpecialGlasses.builder().feature(featureList).special(specialList).build();
    }

    @Override
    public CatalogAndMessage getCatalogAndMessage(String nameOfCatalog) {
        if (nameOfCatalog != null) {
            return CatalogAndMessage.builder().catalog(glassesDao.findAllByName(nameOfCatalog)).message("Found by your request").build();
        } else {
            return CatalogAndMessage.builder().catalog(glassesDao.findAll()).message("Catalog of glasses").build();
        }
    }

    @Override
    public List<Glasses> getCategoryList(String category) {
        return glassesDao.findByCategory(category);
    }

    @Override
    public void save(Glasses glasses) {
        glassesDao.saveGlasses(glasses);
    }

    @Override
    public void deleteById(long id) {
        glassesDao.deleteById(id);
    }

    @Override
    public void update(Glasses glasses) {
        glassesDao.updateById(glasses);
    }
}

