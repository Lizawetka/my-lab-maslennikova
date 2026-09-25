package com.maslennikova.labs;

public class character {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String origin_name;
    private String location_name;
    private String created;
}
public character ( int id, String name, String status, String species, String type,
                   String gender, String origin_name, String location_name, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin_name = origin_name;
        this.location_name = location_name;
        this.created = created;
}

public int getId() {return id;}
public String getName() {return name; }
public String getStatus(){return status;}
public String getSpecies(){return species;}
public String getType(){return type;}
public String getGender(){return gender;}
public String getOrigin_name(){return origin_name;}
public String getLocation_name(){return location_name;}
public String getCreated(){return craeted;}

@Override
public String toString() {return name + "-" + species; }