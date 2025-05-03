package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;

import java.sql.SQLException;

public class UserScreenBuilder implements Builder<Region> {
    private final Account account;
    private final BooleanProperty isLoggedIn;
    private final Runnable returnToAuthenticationScreen;
    private final DBAdapter adapter;

    private final Client client = new Client();
    private final BooleanProperty isClient = new SimpleBooleanProperty(false);

    private final BooleanProperty accountInfoVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty yourCleaningsVisible = new SimpleBooleanProperty(true);
    private final BooleanProperty yourReviewsVisible = new SimpleBooleanProperty(false);

    public UserScreenBuilder(Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
        this.account = account;
        this.isLoggedIn = isLoggedIn;
        this.returnToAuthenticationScreen = returnToAuthenticationScreen;
        this.adapter = adapter;
    }

    private void logOut() {
        isLoggedIn.set(false);
        isClient.set(false);
        account.clear();
        client.clear();
        returnToAuthenticationScreen.run();
    }

    private void logInClient() {
        if (!isLoggedIn.getValue())
            return;

        try {
            client.setClient(adapter.selectClient(account));
            isClient.set(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setVisibility(UserScreen userScreenToBeVisible) {
        accountInfoVisible.set(userScreenToBeVisible == UserScreen.ACCOUNT_INFO);
        yourCleaningsVisible.set(userScreenToBeVisible == UserScreen.CLEANINGS);
        yourReviewsVisible.set(userScreenToBeVisible == UserScreen.REVIEWS);
    }

    @Override
    public Region build() {
        isLoggedIn.addListener((ob, oldVal, newVal) -> logInClient());

        BorderPane window = new BorderPane();

        Button accountInfoButton = new Button("Your Info");
        accountInfoButton.setOnAction(e -> setVisibility(UserScreen.ACCOUNT_INFO));

        Button yourCleaningsButton = new Button("Your Cleanings");
        yourCleaningsButton.setOnAction(e -> setVisibility(UserScreen.CLEANINGS));

        Button yourReviewsButton = new Button("Your Reviews");
        yourReviewsButton.setOnAction(e -> setVisibility(UserScreen.REVIEWS));

        Button logOutButton = new Button("Log Out");
        logOutButton.setOnAction(e -> logOut());

        HBox controlButtons = new HBox(0);
        controlButtons.getChildren().addAll(yourCleaningsButton, yourReviewsButton, accountInfoButton, logOutButton);

        window.setTop(controlButtons);

        Region accountInfo = new UserAccountScreenBuilder(account, isLoggedIn, client, isClient, adapter).build();
        accountInfo.visibleProperty().bind(accountInfoVisible);

        Region yourCleanings = new UserCleaningsScreenBuilder(account, adapter).build();
        yourCleanings.visibleProperty().bind(yourCleaningsVisible);

        Region yourReviews = new UserReviewsScreenBuilder(account, adapter).build();
        yourReviews.visibleProperty().bind(yourReviewsVisible);

        window.setCenter(new StackPane(accountInfo, yourCleanings, yourReviews));

        return window;
    }
}