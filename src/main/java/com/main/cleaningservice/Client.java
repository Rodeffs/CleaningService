package com.main.cleaningservice;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String secondName;
    private ClientType type;
    private Account account;
    private String email;
    private String phone;


    public Client() {
        id = -1;
        name = null;
        surname = null;
        secondName = null;
        type = null;
        account = null;
        email = null;
        phone = null;
    }

    public Client(int id, String name, String surname, String secondName, ClientType type, Account account, String email, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.type = type;
        this.account = account;
        this.email = email;
        this.phone = phone;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void clear() {
        id = -1;
        name = null;
        surname = null;
        secondName = null;
        type = null;
        account = null;
        email = null;
        phone = null;
    }

    public void setClient(Client otherClient) {
        id = otherClient.getId();
        name = otherClient.getName();
        surname = otherClient.getSurname();
        secondName = otherClient.getSecondName();
        type = otherClient.getType();
        account = otherClient.getAccount();
        email = otherClient.getEmail();
        phone = otherClient.getPhone();
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + secondName;
    }
}
