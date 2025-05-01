package com.main.cleaningservice;

import java.sql.Date;
import java.sql.Timestamp;

public class Cleaning {
    private int id;
    private Address address;
    private PlaceType placeType;
    private CleaningType cleaningType;
    private Timestamp time;
    private double totalPrice = 0.0;
    private int cleanersAmount = 0;
    private Client client;

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp time, double totalPrice, int cleanersAmount, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.time = time;
        this.totalPrice = totalPrice;
        this.cleanersAmount = cleanersAmount;
        this.client = client;
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp time, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.time = time;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public CleaningType getCleaningType() {
        return cleaningType;
    }

    public void setCleaningType(CleaningType cleaningType) {
        this.cleaningType = cleaningType;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCleanersAmount() {
        return cleanersAmount;
    }

    public void setCleanersAmount(int cleanersAmount) {
        this.cleanersAmount = cleanersAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
