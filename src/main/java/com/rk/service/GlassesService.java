package com.rk.service;

import com.rk.domain.Glasses;

import java.util.List;

public interface GlassesService {
    List<Glasses> findRandomGlasses(int limit);
}
