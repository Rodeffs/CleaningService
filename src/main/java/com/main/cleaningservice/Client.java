package com.main.cleaningservice;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String secondName;
    private ClientType type;

    public Client(int id, String name, String surname, String secondName, ClientType type) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.surname = surname;
        this.type = type;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }
}
