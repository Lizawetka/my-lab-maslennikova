package com.maslennikova.labs;

import com.maslennikova.labs.config.DatabaseConfig;
import com.maslennikova.labs.model.CityEntity;
import com.maslennikova.labs.repository.CityRepository;
import com.maslennikova.labs.repository.JdbcCityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        DatabaseConfig dbConfig = new DatabaseConfig();
        try {
            dbConfig.runMigrations();
            CityRepository repository = new JdbcCityRepository(dbConfig.getDataSource());

            // Создание
            int moscowId = repository.save(new CityEntity("Москва", 13104177));
            int kazanId = repository.save(new CityEntity("Казань", 1314685));
            log.info("Созданы города с id: {} и {}", moscowId, kazanId);

            //  Поиск по ID
            CityEntity foundById = repository.findById(moscowId);
            log.info("Поиск по id={}: {}", moscowId, foundById);

            // ПОиск по имени
            CityEntity foundByName = repository.findByName("Казань");
            log.info("Поиск по имени 'Казань': {}", foundByName);

            // Все города
            List<CityEntity> allCities = repository.findAll();
            log.info("Все города в БД: {}", allCities);

            // обновление
            foundById.setPopulation(13200000);
            boolean isUpdated = repository.update(foundById);
            log.info("Обновление города id={}: успех={}, новые данные={}",
                    moscowId, isUpdated, repository.findById(moscowId));

            repository.deleteById(kazanId);
            repository.deleteById(kazanId);
            log.info("После удаления id={}: поиск возвращает {}", kazanId, repository.findById(kazanId));

        } finally {
            dbConfig.close();
        }
    }
}