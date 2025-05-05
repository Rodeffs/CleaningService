package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty login = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final AccountType type = new AccountType();
    private final StringProperty displayName = new SimpleStringProperty();

    public Account() {
        this.id.set(-1);
        this.login.set("");
        this.password.set("");
        this.displayName.set("");
    }

    public Account(int id, String login, String password, AccountType type, String displayName) {
        this.id.set(id);
        this.login.set(login);
        this.password.set(password);
        this.type.set(type);
        this.displayName.set(displayName);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getLogin() {
        return login.get();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type.set(type);
    }

    public String getDisplayName() {
        return displayName.get();
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    public void set(Account otherAccount) {
        this.id.set(otherAccount.getId());
        this.login.set(otherAccount.getLogin());
        this.password.set(otherAccount.getPassword());
        this.type.set(otherAccount.getType());
        this.displayName.set(otherAccount.getDisplayName());
    }

    public void clear() {
        this.id.set(-1);
        this.login.set("");
        this.password.set("");
        this.type.clear();
        this.displayName.set("");
    }
}
