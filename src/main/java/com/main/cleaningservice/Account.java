package com.main.cleaningservice;

public class Account {
    private int id;
    private String login;
    private String password;
    private AccountType type;
    private String displayName;

    public Account(int id, String login, String password, AccountType type, String displayName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = type;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return  password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
