package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientType {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    public ClientType() {
        this.id.set(-1);
        this.name.set("");
    }

    public ClientType(int id, String name) {
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

    public void set(ClientType otherType) {
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
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof ClientType))
            return false;

        ClientType otherType = (ClientType)obj;

        return name.get().equals(otherType.getName());
    }
}
