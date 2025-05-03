package com.main.cleaningservice;

import java.sql.Date;

public class Cleaner {
    private int id;
    private String name;
    private String surname;
    private String secondName;
    private Date birthday;

    public Cleaner(int id, String name, String surname, String secondName, Date birthday) {
        this.id = id;
        this.birthday = birthday;
        this.secondName = secondName;
        this.surname = surname;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
