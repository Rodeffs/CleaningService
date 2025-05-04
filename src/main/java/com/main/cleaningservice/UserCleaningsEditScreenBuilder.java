package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class UserCleaningsEditScreenBuilder implements Builder<Region> {
    private final DBAdapter adapter;
    private final Client client;
    private final BooleanProperty isClient;
    private final Cleaning selectedCleaning;
    private final Runnable exitScreen;

    public UserCleaningsEditScreenBuilder(DBAdapter adapter, Client client, BooleanProperty isClient, Cleaning selectedCleaning, Runnable exitScreen) {
        this.adapter = adapter;
        this.client = client;
        this.isClient = isClient;
        this.selectedCleaning = selectedCleaning;
        this.exitScreen = exitScreen;
    }

    @Override
    public Region build() {
        ScrollPane window = new ScrollPane();

        return window;
    }
}
