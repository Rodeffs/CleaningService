package com.main.cleaningservice;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Timestamp;

public class Cleaning {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final Address address = new Address();
    private final PlaceType placeType = new PlaceType();
    private final CleaningType cleaningType = new CleaningType();
    private final Timestamp timestamp = new Timestamp(0);
    private final DoubleProperty totalPrice = new SimpleDoubleProperty();
    private final IntegerProperty cleanersAmount = new SimpleIntegerProperty();
    private final Client client = new Client();

    public Cleaning() {
        this.id.set(-1);
        this.totalPrice.set(0.0);
        this.cleanersAmount.set(0);
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, double totalPrice, int cleanersAmount, Client client) {
        this.id.set(id);
        this.address.set(address);
        this.placeType.set(placeType);
        this.cleaningType.set(cleaningType);
        this.timestamp.setTime(timestamp.getTime());
        this.totalPrice.set(totalPrice);
        this.cleanersAmount.set(cleanersAmount);
        this.client.set(client);
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, double totalPrice, Client client) {
        this.id.set(id);
        this.address.set(address);
        this.placeType.set(placeType);
        this.cleaningType.set(cleaningType);
        this.timestamp.setTime(timestamp.getTime());
        this.totalPrice.set(totalPrice);
        this.cleanersAmount.set(0);
        this.client.set(client);
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Timestamp timestamp, Client client) {
        this.id.set(id);
        this.address.set(address);
        this.placeType.set(placeType);
        this.cleaningType.set(cleaningType);
        this.timestamp.setTime(timestamp.getTime());
        this.totalPrice.set(0.0);
        this.cleanersAmount.set(0);
        this.client.set(client);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType.set(placeType);
    }

    public CleaningType getCleaningType() {
        return cleaningType;
    }

    public void setCleaningType(CleaningType cleaningType) {
        this.cleaningType.set(cleaningType);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp.setTime(timestamp.getTime());
    }

    public double getTotalPrice() {
        return totalPrice.getValue();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public int getCleanersAmount() {
        return cleanersAmount.getValue();
    }

    public void setCleanersAmount(int cleanersAmount) {
        this.cleanersAmount.set(cleanersAmount);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client.set(client);
    }

    public void set(Cleaning otherCleaning) {
        this.id.set(otherCleaning.getId());
        this.address.set(otherCleaning.getAddress());
        this.placeType.set(otherCleaning.getPlaceType());
        this.cleaningType.set(otherCleaning.getCleaningType());
        this.timestamp.setTime(timestamp.getTime());
        this.totalPrice.set(otherCleaning.getTotalPrice());
        this.cleanersAmount.set(otherCleaning.getCleanersAmount());
        this.client.set(otherCleaning.getClient());
    }

    public void clear() {
        this.id.set(-1);
        this.address.clear();
        this.placeType.clear();
        this.cleaningType.clear();
        this.timestamp.setTime(0);
        this.totalPrice.set(0.0);
        this.cleanersAmount.set(0);
        this.client.clear();
    }
}
