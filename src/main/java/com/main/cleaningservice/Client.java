package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty surname = new SimpleStringProperty();
    private final StringProperty secondName = new SimpleStringProperty();
    private final ClientType type = new ClientType();
    private final Account account = new Account();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();


    public Client() {
        this.id.set(-1);
        this.name.set("");
        this.surname.set("");
        this.secondName.set("");
        this.email.set("");
        this.phone.set("");
    }

    public Client(int id, String name, String surname, String secondName, ClientType type, Account account, String email, String phone) {
        this.id.set(id);
        this.name.set(name);
        this.surname.set(surname);
        this.secondName.set(secondName);
        this.type.set(type);
        this.account.set(account);
        this.email.set(email);
        this.phone.set(phone);
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

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getSecondName() {
        return secondName.get();
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type.set(type);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account.set(account);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void set(Client otherClient) {
        this.id.set(otherClient.getId());
        this.name.set(otherClient.getName());
        this.surname.set(otherClient.getSurname());
        this.secondName.set(otherClient.getSecondName());
        this.type.set(otherClient.getType());
        this.account.set(otherClient.getAccount());
        this.email.set(otherClient.getEmail());
        this.phone.set(otherClient.getPhone());
    }

    public void clear() {
        this.id.set(-1);
        this.name.set("");
        this.surname.set("");
        this.secondName.set("");
        this.type.clear();
        this.account.clear();
        this.email.set("");
        this.phone.set("");
    }

    @Override
    public String toString() {
        return name.get() + " " + surname.get() + " " + secondName.get();
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + secondName;
    }
}
