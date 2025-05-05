package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Builder;

public class AdminScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final Account account;
    private final Runnable returnToAuthenticationScreen;
    private final DBAdapter adapter;
    private final BooleanProperty isLoggedIn;

    private final BooleanProperty isAdmin = new SimpleBooleanProperty(false);

    private final BooleanProperty cleaningsVisible = new SimpleBooleanProperty(true);
    private final BooleanProperty accountInfoVisible = new SimpleBooleanProperty(false);

    public AdminScreenBuilder(Stage stage, Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
        this.stage = stage;
        this.account = account;
        this.isLoggedIn = isLoggedIn;
        this.returnToAuthenticationScreen = returnToAuthenticationScreen;
        this.adapter = adapter;
    }

    private void logOut() {
        isLoggedIn.set(false);
        isAdmin.set(false);
        account.clear();
        returnToAuthenticationScreen.run();
    }

    private void setVisibility(AdminScreen userScreenToBeVisible) {
        accountInfoVisible.set(userScreenToBeVisible == AdminScreen.ACCOUNT_INFO);
        cleaningsVisible.set(userScreenToBeVisible == AdminScreen.CLEANINGS);
    }

    private void logInAdmin() {
        if (!isLoggedIn.getValue())
            return;

        isAdmin.set(account.getType().getName().equals("admin"));
    }

    @Override
    public Region build() {
        isLoggedIn.addListener((ob, oldVal, newVal) -> logInAdmin());

        BorderPane window = new BorderPane();

        Button accountInfoButton = new Button("Your Account");
        accountInfoButton.setOnAction(e -> setVisibility(AdminScreen.ACCOUNT_INFO));

        Button cleaningsButton = new Button("Cleanings");
        cleaningsButton.setOnAction(e -> setVisibility(AdminScreen.CLEANINGS));

        Button logOutButton = new Button("Log Out");
        logOutButton.setOnAction(e -> logOut());

        HBox controlButtons = new HBox(0);
        controlButtons.getChildren().addAll(cleaningsButton, accountInfoButton, logOutButton);

        window.setTop(controlButtons);

        Region accountInfo = new AdminAccountScreenBuilder(stage, account, isLoggedIn, isAdmin, adapter).build();
        accountInfo.visibleProperty().bind(accountInfoVisible);

        Region cleanings = new AdminCleaningsScreenBuilder(stage, isAdmin, adapter).build();
        cleanings.visibleProperty().bind(cleaningsVisible);

        window.setCenter(new StackPane(accountInfo, cleanings));

        return window;
    }
}
