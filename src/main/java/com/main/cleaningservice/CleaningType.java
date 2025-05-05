package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CleaningType {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    public CleaningType() {
        this.id.set(-1);
        this.name.set("");
    }

    public CleaningType(int id, String name) {
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

    public void set(CleaningType otherType) {
        this.id.set(otherType.getId());
        this.name.set(otherType.getName());
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
        if ((otherObj == null) || !(otherObj instanceof CleaningType))
            return false;

        CleaningType otherCleaningType = (CleaningType) otherObj;

        return (id.get() == otherCleaningType.getId()) && (name.get().equals(otherCleaningType.getName()));
    }
}
