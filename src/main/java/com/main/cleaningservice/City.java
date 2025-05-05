package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final Country country = new Country();
    private final StringProperty name = new SimpleStringProperty();

    public City() {
        this.id.set(-1);
        this.name.set("");
    }

    public City(int id, Country country, String name) {
        this.id.set(id);
        this.country.set(country);
        this.name.set(name);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country.set(country);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void set(City otherCity) {
        this.id.set(otherCity.getId());
        this.country.set(otherCity.getCountry());
        this.name.set(otherCity.getName());
    }

    public void clear() {
        this.id.set(-1);
        this.country.clear();
        this.name.set("");
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean equals(Object otherObject) {
        if ((otherObject == null) || !(otherObject instanceof City))
            return false;

        City otherCity = (City) otherObject;

        return (id.get() == otherCity.getId()) && (name.get().equals(otherCity.getName())) && (country.equals(otherCity.getCountry()));
    }
}
