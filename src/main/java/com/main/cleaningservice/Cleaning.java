package com.main.cleaningservice;

import java.sql.Date;

public class Cleaning {
    private int id;
    private Address address;
    private PlaceType placeType;
    private CleaningType cleaningType;
    private Date timeDate;
    private int totalPrice = 0;
    private int cleanersAmount = 0;
    private Client client;

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Date timeDate, int totalPrice, int cleanersAmount, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.timeDate = timeDate;
        this.totalPrice = totalPrice;
        this.cleanersAmount = cleanersAmount;
        this.client = client;
    }

    public Cleaning(int id, Address address, PlaceType placeType, CleaningType cleaningType, Date timeDate, Client client) {
        this.id = id;
        this.address = address;
        this.placeType = placeType;
        this.cleaningType = cleaningType;
        this.timeDate = timeDate;
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

    public Date getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(Date timeDate) {
        this.timeDate = timeDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
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
