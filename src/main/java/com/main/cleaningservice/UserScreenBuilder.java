package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class UserScreenBuilder implements Builder<Region> {
    Account account;
    Runnable returnToAuthenticationScreen;
    DBAdapter adapter;
    BooleanProperty isLoggedIn;

    public UserScreenBuilder(Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
        this.account = account;
        this.isLoggedIn = isLoggedIn;
        this.returnToAuthenticationScreen = returnToAuthenticationScreen;
        this.adapter = adapter;
    }

    private void logOut() {
        isLoggedIn.set(false);
        returnToAuthenticationScreen.run();
    }

    @Override
    public Region build() {
        BorderPane window = new BorderPane();

        Button accountInfoButton = new Button("Account Info");

        Button yourCleaningsButton = new Button("Your Cleanings");

        Button yourReviewsButton = new Button("Your Reviews");

        Button logOutButton = new Button("Log Out");
        logOutButton.setOnAction(e -> logOut());

        HBox controlButtons = new HBox(0);
        controlButtons.getChildren().addAll(accountInfoButton, yourCleaningsButton, yourReviewsButton, logOutButton);

        window.setTop(controlButtons);

        return window;
    }
}