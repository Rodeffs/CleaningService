package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;

public class MainScreenBuilder implements Builder<Region> {
    Account account;
    Runnable returnToAuthenticationScreen;
    DBAdapter adapter;
    BooleanProperty isLoggedIn;
    BooleanProperty adminGUIVisible = new SimpleBooleanProperty(false);

    public MainScreenBuilder(Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
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

        Region adminGUI = new AdminScreenBuilder(account, isLoggedIn, adapter, returnToAuthenticationScreen).build();
        Region userGUI = new UserScreenBuilder(account, isLoggedIn, adapter, returnToAuthenticationScreen).build();

        adminGUI.visibleProperty().bind(adminGUIVisible);
        userGUI.visibleProperty().bind(adminGUIVisible.not());

        return new StackPane(adminGUI, userGUI);
    }
}
