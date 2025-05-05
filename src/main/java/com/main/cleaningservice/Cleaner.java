package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class Cleaner {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty surname = new SimpleStringProperty();
    private final StringProperty secondName = new SimpleStringProperty();
    private final Date birthday = new Date(0);

    public Cleaner() {
        this.id.set(-1);
        this.name.set("");
        this.surname.set("");
        this.secondName.set("");
    }

    public Cleaner(int id, String name, String surname, String secondName, Date birthday) {
        this.id.set(id);
        this.name.set(name);
        this.surname.set(surname);
        this.secondName.set(secondName);
        this.birthday.setTime(birthday.getTime());
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

    public String getSecondName() {
        return secondName.get();
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday.setTime(birthday.getTime());
    }

    @Override
    public String toString() {
        return name.get() + " " + surname.get() + " " + secondName.get();
    }
}
