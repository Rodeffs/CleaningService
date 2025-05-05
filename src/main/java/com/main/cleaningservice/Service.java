package com.main.cleaningservice;

import javafx.beans.property.*;

public class Service {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();

    public Service() {
        this.id.set(-1);
        this.name.set("");
        this.description.set("");
        this.price.set(0.0);
    }

    public Service(int id, String name, String description, double price) {
        this.id.set(id);
        this.name.set(name);
        this.description.set(description);
        this.price.set(price);
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

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void set(Service otherService) {
        this.id.set(otherService.getId());
        this.name.set(otherService.getName());
        this.description.set(otherService.getDescription());
        this.price.set(otherService.getPrice());
    }

    public void clear() {
        this.id.set(-1);
        this.name.set("");
        this.description.set("");
        this.price.set(0.0);
    }

    @Override
    public String toString() {
        return name.get() + " - " + price.get();
    }
}
