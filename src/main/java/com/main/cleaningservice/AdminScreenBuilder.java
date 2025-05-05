package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Builder;

public class AdminScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final Account account;
    private final Runnable returnToAuthenticationScreen;
    private final DBAdapter adapter;
    private final BooleanProperty isLoggedIn;

    public AdminScreenBuilder(Stage stage, Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
        this.stage = stage;
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
