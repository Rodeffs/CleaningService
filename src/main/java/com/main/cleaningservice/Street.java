package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Street {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final City city = new City();
    private final StringProperty name = new SimpleStringProperty();

    public Street() {
        this.id.set(-1);
        this.name.set("");
    }

    public Street(int id, City city, String name) {
        this.id.set(id);
        this.city.set(city);
        this.name.set(name);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city.set(city);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void set(Street otherStreet) {
        this.id.set(otherStreet.getId());
        this.city.set(otherStreet.getCity());
        this.name.set(otherStreet.getName());
    }

    public void clear() {
        this.id.set(-1);
        this.city.clear();
        this.name.set("");
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean equals(Object otherObj) {
        if ((otherObj == null) || !(otherObj instanceof Street))
            return false;

        Street otherStreet = (Street) otherObj;

        return (id.get() == otherStreet.getId()) && (name.get().equals(otherStreet.getName())) && (city.equals(otherStreet.getCity()));
    }
}
