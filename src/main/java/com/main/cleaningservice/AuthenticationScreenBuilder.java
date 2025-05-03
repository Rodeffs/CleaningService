package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Builder;

public class AuthenticationScreenBuilder implements Builder<Region> {
    Account account;
    Runnable exitAuthenticationScreen;
    BooleanProperty isLoggedIn;
    DBAdapter adapter;

    public AuthenticationScreenBuilder(Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable exitAuthenticationScreen) {
        this.account = account;
        this.adapter = adapter;
        this.isLoggedIn = isLoggedIn;
        this.exitAuthenticationScreen = exitAuthenticationScreen;
    }

    @Override
    public Region build() {
        ScrollPane window = new ScrollPane(); // so that you can scroll if contents don't fit

        BooleanProperty loginScreenVisible = new SimpleBooleanProperty(true);

        Region loginWindow = new LoginScreenBuilder(account, isLoggedIn, adapter, exitAuthenticationScreen, () -> loginScreenVisible.set(false)).build();
        Region registrationWindow = new RegistrationScreenBuilder(account, isLoggedIn, adapter, exitAuthenticationScreen, () -> loginScreenVisible.set(true)).build();

        loginWindow.visibleProperty().bind(loginScreenVisible);
        registrationWindow.visibleProperty().bind(loginScreenVisible.not());

        window.setContent(new StackPane(loginWindow, registrationWindow));
        return window;
    }
}
