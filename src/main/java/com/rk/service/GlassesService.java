package com.rk.service;

import com.rk.domain.Glasses;
import com.rk.dto.CatalogAndMessage;
import com.rk.dto.FeaturesAndSpecialGlasses;

import java.util.List;

public interface GlassesService {

    Glasses findById(long id);

    FeaturesAndSpecialGlasses getListsFeaturesAndSpecialGlasses(int limitFeature, int limitSpecial);

    CatalogAndMessage searchGlasses(String catalog);

    CatalogAndMessage getCatalog();

    List<Glasses> getCategoryList(String category);

    void save(Glasses glasses);

    void deleteById(long id);

    void update(Glasses glasses);
}
