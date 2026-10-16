package com.maslennikova.labs;

public class Character {
    private String name;
    private String status;
    private String species;
    private String gender;

    public Character(String name, String status, String species, String gender) {
        this.name = name;
        this.status = status;
        this.species = species;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getGender() {
        return gender;
    }
}
