package com.main.cleaningservice;

public class City {
    private int id;
    private Country country;
    private String name;

    public City(int id, Country country, String name) {
        this.id = id;
        this.country = country;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object otherObject) {
        if ((otherObject == null) || !(otherObject instanceof City))
            return false;

        City otherCity = (City) otherObject;

        return (id == otherCity.getId()) && (name.equals(otherCity.getName())) && (country.equals(otherCity.getCountry()));
    }
}
