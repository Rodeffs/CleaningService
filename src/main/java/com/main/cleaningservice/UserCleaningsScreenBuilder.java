package com.main.cleaningservice;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class UserCleaningsScreenBuilder implements Builder<Region> {
    Account account;
    Runnable returnToAuthenticationScreen;
    DBAdapter adapter;

    public UserCleaningsScreenBuilder(Account account, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
        this.account = account;
        this.returnToAuthenticationScreen = returnToAuthenticationScreen;
        this.adapter = adapter;
    }

    @Override
    public Region build() {
        GridPane window = new GridPane();

        return window;
    }

}
