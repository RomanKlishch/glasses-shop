package com.rk.service;

import com.rk.domain.Glasses;

import java.util.List;

public interface GlassesService {
    List<Glasses> findRandomGlasses(int limit);

    Glasses findById(long id);

    List<Glasses> findAll();

    List<Glasses> findByName(String name);
}
