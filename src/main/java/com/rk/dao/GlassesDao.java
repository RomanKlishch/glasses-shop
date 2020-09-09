package com.rk.dao;

import com.rk.domain.Glasses;

import java.util.List;

public interface GlassesDao {
    List<Glasses> findAll();

    List<Glasses> findListOfRandom(int limit);

    List<Glasses> findAllByName(String name);

    Glasses findById(long id);

    void saveGlasses(Glasses glasses);

    void updateById(Glasses glasses);

    void deleteById(long id);

    List<Glasses> findByCategory(String category);
}
