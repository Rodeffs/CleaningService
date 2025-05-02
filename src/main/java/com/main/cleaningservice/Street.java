package com.main.cleaningservice;

public class Street {
    private int id;
    private City city;
    private String name;

    public Street(int id, City city, String name) {
        this.id = id;
        this.city = city;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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
}
