package com.maslennikova.labs.repository;

import com.maslennikova.labs.model.CityEntity;
import java.util.List;

public interface CityRepository {
    int save(CityEntity entity);
    CityEntity findById(int id);
    CityEntity findByName(String name);
    List<CityEntity> findAll();
    boolean update(CityEntity entity);
    void deleteById(int id);
}