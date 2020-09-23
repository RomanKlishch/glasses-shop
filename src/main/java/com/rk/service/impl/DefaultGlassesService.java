package com.rk.service.impl;

import com.rk.ServiceLocator;
import com.rk.dao.GlassesDao;
import com.rk.dao.jdbc.JdbcGlassesDao;
import com.rk.domain.Glasses;
import com.rk.domain.Photo;
import com.rk.dto.CatalogAndMessage;
import com.rk.dto.FeaturesAndSpecialGlasses;
import com.rk.service.GlassesService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DefaultGlassesService implements GlassesService {
    private final GlassesDao glassesDao = ServiceLocator.getBean(GlassesDao.class);

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
        return glassesDao.findByCategory(category);
    }

    @Override
    public void save(Glasses glasses, String[] arrayAddress) {
        List<Photo> photoList = new ArrayList<>();
        if (arrayAddress!=null){
            for (String address : arrayAddress) {
                if (!address.isEmpty()) {
                    Photo photo = new Photo();
                    photo.setAddress(address);
                    photoList.add(photo);
                }
            }
        }
        glasses.setPhotos(photoList);
        glassesDao.saveGlasses(glasses);
    }

    @Override
    public void deleteById(long id) {
        glassesDao.deleteById(id);
    }

    @Override
    public void update(Glasses glasses, String[] id, String[] address) {
        List<Photo> photoList = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            if (!id[i].isEmpty() && !address[i].isEmpty()) {
                photoList.add(Photo.builder()
                        .id(Long.parseLong(id[i])).address(address[i]).build());
            }
        }
        glasses.setPhotos(photoList);
        glassesDao.updateById(glasses);
    }

}

