package com.rk.service;

import com.rk.domain.Glasses;
import com.rk.dto.CatalogAndMessage;
import com.rk.dto.FeaturesAndSpecialGlasses;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GlassesService {

    Glasses findById(long id);

    List<Glasses> findAll();

    List<Glasses> findByName(String name);

    FeaturesAndSpecialGlasses getListsFeaturesAndSpecialGlasses(int limitFeature, int limitSpecial);

    CatalogAndMessage getCatalogAndMessage(HttpServletRequest request);

    List<Glasses> getCategoryList(String category);

    void save(Glasses glasses, String[] arrayAddress);
}
