package com.main.cleaningservice;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class MainGUIBuilder implements Builder<Region> {
    Account account;
    Runnable returnToAuthenticationScreen;
    DBAdapter adapter;

    public MainGUIBuilder(Account account, DBAdapter adapter, Runnable returnToAuthenticationScreen) {
        this.account = account;
        this.returnToAuthenticationScreen = returnToAuthenticationScreen;
        this.adapter = adapter;
    }

    @Override
    public Region build() {
        BorderPane window = new BorderPane();

        Text test = new Text("this is just sample text");
        window.setCenter(test);

        return window;
    }
}
