package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Country {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    public Country() {
        this.id.set(-1);
        this.name.set("");
    }

    public Country(int id, String name) {
        this.id.set(id);
        this.name.set(name);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void set(Country otherCountry) {
        this.id.set(otherCountry.getId());
        this.name.set(otherCountry.getName());
    }

    public void clear() {
        this.id.set(-1);
        this.name.set("");
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean equals(Object otherObj) {
        if ((otherObj == null) || !(otherObj instanceof Country))
            return false;

        Country otherCountry = (Country) otherObj;

        return (id.get() == otherCountry.getId()) && (name.get().equals(otherCountry.getName()));
    }
}
