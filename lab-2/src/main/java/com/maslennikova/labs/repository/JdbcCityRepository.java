package com.maslennikova.labs.repository;

import com.maslennikova.labs.exception.DatabaseException;
import com.maslennikova.labs.model.CityEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCityRepository implements CityRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcCityRepository.class);
    private final DataSource dataSource;

    public JdbcCityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int save(CityEntity entity) {
        String sql = "INSERT INTO city (name, population) VALUES (?, ?) RETURNING id";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getPopulation());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    entity.setId(id);
                    log.debug("Сохранен город с id={}", id);
                    return id;
                }
                throw new DatabaseException("Не удалось получить id после сохранения города", null);
            }
        } catch (SQLException e) {
            log.error("Ошибка при сохранении города: {}", entity, e);
            throw new DatabaseException("Ошибка при сохранении города", e);
        }
    }

    @Override
    public CityEntity findById(int id) {
        String sql = "SELECT id, name, population FROM city WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            log.error("Ошибка при поиске города по id={}", id, e);
            throw new DatabaseException("Ошибка при поиске города по id", e);
        }
    }

    @Override
    public CityEntity findByName(String name) {
        String sql = "SELECT id, name, population FROM city WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            log.error("Ошибка при поиске города по имени={}", name, e);
            throw new DatabaseException("Ошибка при поиске города по имени", e);
        }
    }

    @Override
    public List<CityEntity> findAll() {
        String sql = "SELECT id, name, population FROM city ORDER BY id";
        List<CityEntity> cities = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cities.add(mapRow(rs));
            }
            return cities;
        } catch (SQLException e) {
            log.error("Ошибка при получении списка всех городов", e);
            throw new DatabaseException("Ошибка при получении списка городов", e);
        }
    }

    @Override
    public boolean update(CityEntity entity) {
        if (entity.getId() == null) {
            return false;
        }
        String sql = "UPDATE city SET name = ?, population = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getPopulation());
            ps.setInt(3, entity.getId());

            int updatedRows = ps.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            log.error("Ошибка при обновлении города: {}", entity, e);
            throw new DatabaseException("Ошибка при обновлении города", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM city WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            log.debug("Выполнено удаление города с id={}", id);
        } catch (SQLException e) {
            log.error("Ошибка при удалении города с id={}", id, e);
            throw new DatabaseException("Ошибка при удалении города", e);
        }
    }

    private CityEntity mapRow(ResultSet rs) throws SQLException {
        return new CityEntity(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("population")
        );
    }
}