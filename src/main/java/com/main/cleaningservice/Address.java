package com.main.cleaningservice;

public class Address {
    private int id;
    private Street street;
    private int buildingNumber;
    private int entranceNumber;
    private int floorNumber;
    private int unitNumber;

    public Address(int id, Street street, int buildingNumber, int entranceNumber, int floorNumber, int unitNumber) {
        this.id = id;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.entranceNumber = entranceNumber;
        this.floorNumber = floorNumber;
        this.unitNumber = unitNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public int getEntranceNumber() {
        return entranceNumber;
    }

    public void setEntranceNumber(int entranceNumber) {
        this.entranceNumber = entranceNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }
}
