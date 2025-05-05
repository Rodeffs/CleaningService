package com.main.cleaningservice;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Timestamp;

public class Cleaning {
    private int id;
    private Address address;
    private PlaceType placeType;
    private CleaningType cleaningType;
    private Timestamp timestamp;
    private DoubleProperty totalPrice;  // otherwise they won't get updated in table
    private IntegerProperty cleanersAmount;
    private Client client;

    public Cleaning() {
        id = -1;
        address = null;
        placeType = null;
        cleaningType = null;
        timestamp = null;
        totalPrice = new SimpleDoubleProperty(0.0);
        cleanersAmount = new SimpleIntegerProperty(0);
        client = null;
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, double totalPrice, int cleanersAmount, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.timestamp = timestamp;
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.cleanersAmount = new SimpleIntegerProperty(cleanersAmount);
        this.client = client;
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, double totalPrice, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.timestamp = timestamp;
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.cleanersAmount = new SimpleIntegerProperty(0);
        this.client = client;
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.timestamp = timestamp;
        this.totalPrice = new SimpleDoubleProperty(0.0);
        this.cleanersAmount = new SimpleIntegerProperty(0);
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
        return totalPrice.getValue();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public DoubleProperty getTotalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPriceProperty(DoubleProperty totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCleanersAmount() {
        return cleanersAmount.getValue();
    }

    public void setCleanersAmount(int cleanersAmount) {
        this.cleanersAmount.set(cleanersAmount);
    }

    public IntegerProperty getCleanersAmountProperty() {
        return cleanersAmount;
    }

    public void setCleanersAmountProperty(IntegerProperty cleanersAmount) {
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
        totalPrice = otherCleaning.getTotalPriceProperty();
        cleanersAmount = otherCleaning.getCleanersAmountProperty();
        client = otherCleaning.getClient();
    }

    public void clear() {
        id = -1;
        address = null;
        placeType = null;
        cleaningType = null;
        timestamp = null;
        totalPrice = new SimpleDoubleProperty(0.0);
        cleanersAmount = new SimpleIntegerProperty(0);
        client = null;
    }
}
