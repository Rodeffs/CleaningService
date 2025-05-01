package com.main.cleaningservice;

import java.sql.Date;

public class Cleaner {
    private int id;
    private String name;
    private String surname;
    private String second_name;
    private Date birthday;

    public Cleaner(int id, Date birthday, String second_name, String surname, String name) {
        this.id = id;
        this.birthday = birthday;
        this.second_name = second_name;
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

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
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
