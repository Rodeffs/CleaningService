package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Builder;

public class MainScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final Account account;
    private final Runnable returnToAuthenticationScreen;
    private final DBAdapter adapter;
    private final BooleanProperty isLoggedIn;
    private final BooleanProperty adminGUIVisible = new SimpleBooleanProperty(false);

    public MainScreenBuilder(Stage stage, Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
        this.stage = stage;
        this.account = account;
        this.isLoggedIn = isLoggedIn;
        this.returnToAuthenticationScreen = returnToAuthenticationScreen;
        this.adapter = adapter;
    }

    private void showAdminGUI() {
        if (isLoggedIn.getValue())
            adminGUIVisible.set(account.getType().getName().equals("admin"));
    }

    @Override
    public Region build() {
        isLoggedIn.addListener((ob, oldVal, newVal) -> showAdminGUI());

        Region adminGUI = new AdminScreenBuilder(stage, account, isLoggedIn, adapter, returnToAuthenticationScreen).build();
        Region userGUI = new UserScreenBuilder(stage, account, isLoggedIn, adapter, returnToAuthenticationScreen).build();

        adminGUI.visibleProperty().bind(adminGUIVisible);
        userGUI.visibleProperty().bind(adminGUIVisible.not());

        return new StackPane(adminGUI, userGUI);
    }
}
