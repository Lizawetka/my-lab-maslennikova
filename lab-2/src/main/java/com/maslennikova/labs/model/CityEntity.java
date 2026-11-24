package com.maslennikova.labs.model;

public class CityEntity {
    private Integer id;
    private String name;
    private Integer population;

    public CityEntity(String name, Integer population) {
        this.name = name;
        this.population = population;
    }

    public CityEntity(Integer id, String name, Integer population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "CityEntity{id=" + id + ", name='" + name + "', population=" + population + "}";
    }
}
