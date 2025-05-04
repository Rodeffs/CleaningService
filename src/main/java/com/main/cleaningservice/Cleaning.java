package com.main.cleaningservice;

import java.sql.Timestamp;

public class Cleaning {
    private int id;
    private Address address;
    private PlaceType placeType;
    private CleaningType cleaningType;
    private Timestamp timestamp;
    private double totalPrice;
    private int cleanersAmount;
    private Client client;

    public Cleaning() {
        id = -1;
        address = null;
        placeType = null;
        cleaningType = null;
        timestamp = null;
        totalPrice = 0.0;
        cleanersAmount = 0;
        client = null;
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, double totalPrice, int cleanersAmount, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.timestamp = timestamp;
        this.totalPrice = totalPrice;
        this.cleanersAmount = cleanersAmount;
        this.client = client;
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, double totalPrice, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.timestamp = timestamp;
        this.totalPrice = totalPrice;
        this.cleanersAmount = 0;
        this.client = client;
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.timestamp = timestamp;
        this.totalPrice = 0.0;
        this.cleanersAmount = 0;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

    public void setCleaning(Cleaning otherCleaning) {
        id = otherCleaning.getId();
        address = otherCleaning.getAddress();
        placeType = otherCleaning.getPlaceType();
        cleaningType = otherCleaning.getCleaningType();
        timestamp = otherCleaning.getTimestamp();
        totalPrice = otherCleaning.getTotalPrice();
        cleanersAmount = otherCleaning.getCleanersAmount();
        client = otherCleaning.getClient();
    }
}
