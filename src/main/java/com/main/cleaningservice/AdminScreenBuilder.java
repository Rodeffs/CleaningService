package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class AdminScreenBuilder implements Builder<Region> {
    Account account;
    Runnable returnToAuthenticationScreen;
    DBAdapter adapter;
    BooleanProperty isLoggedIn;

    public AdminScreenBuilder(Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
        this.account = account;
        this.isLoggedIn = isLoggedIn;
        this.returnToAuthenticationScreen = returnToAuthenticationScreen;
        this.adapter = adapter;
    }

    @Override
    public Region build() {
        BorderPane window = new BorderPane();

        //isLoggedIn.addListener((ob, oldVal, newVal) -> setText(test));

        return window;
    }
}
