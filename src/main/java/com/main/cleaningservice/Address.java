package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Address {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final Street street = new Street();
    private final IntegerProperty buildingNumber = new SimpleIntegerProperty();
    private final IntegerProperty entranceNumber = new SimpleIntegerProperty();
    private final IntegerProperty floorNumber = new SimpleIntegerProperty();
    private final IntegerProperty unitNumber = new SimpleIntegerProperty();

    public Address() {
        this.id.set(-1);
        this.buildingNumber.set(-1);
        this.entranceNumber.set(-1);
        this.floorNumber.set(-1);
        this.unitNumber.set(-1);
    }

    public Address(int id, Street street, int buildingNumber, int entranceNumber, int floorNumber, int unitNumber) {
        this.id.set(id);
        this.street.set(street);
        this.buildingNumber.set(buildingNumber);
        this.entranceNumber.set(entranceNumber);
        this.floorNumber.set(floorNumber);
        this.unitNumber.set(unitNumber);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street.set(street);
    }

    public int getBuildingNumber() {
        return buildingNumber.get();
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber.set(buildingNumber);
    }

    public int getEntranceNumber() {
        return entranceNumber.get();
    }

    public void setEntranceNumber(int entranceNumber) {
        this.entranceNumber.set(entranceNumber);
    }

    public int getFloorNumber() {
        return floorNumber.get();
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber.set(floorNumber);
    }

    public int getUnitNumber() {
        return unitNumber.get();
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber.set(unitNumber);
    }

    public void set(Address otherAddress) {
        this.id.set(otherAddress.getId());
        this.street.set(otherAddress.getStreet());
        this.buildingNumber.set(otherAddress.getBuildingNumber());
        this.entranceNumber.set(otherAddress.getEntranceNumber());
        this.floorNumber.set(otherAddress.getFloorNumber());
        this.unitNumber.set(otherAddress.getUnitNumber());
    }

    public void clear() {
        this.id.set(-1);
        this.street.clear();
        this.buildingNumber.set(-1);
        this.entranceNumber.set(-1);
        this.floorNumber.set(-1);
        this.unitNumber.set(-1);
    }

    @Override
    public String toString() {
        return street.getCity().getCountry().getName() + "\n" + street.getCity().getName() + "\n" + street.getName() + ", " + buildingNumber + "\nEntrance " + entranceNumber + "\nFloor " + floorNumber + "\nUnit " + unitNumber;
    }

    @Override
    public boolean equals(Object otherObj) {
        if ((otherObj == null) || !(otherObj instanceof Address))
            return false;

        Address otherAddress = (Address) otherObj;

        return (id.get() == otherAddress.getId()) && (street.equals(otherAddress.getStreet())) && (buildingNumber.get() == otherAddress.getBuildingNumber()) && (entranceNumber.get() == otherAddress.getEntranceNumber()) && (floorNumber.get() == otherAddress.getFloorNumber()) && (unitNumber.get() == otherAddress.getUnitNumber());
    }
}
