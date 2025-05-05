package com.main.cleaningservice;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Builder;

public class UserReviewsScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final Account account;
    private final DBAdapter adapter;

    public UserReviewsScreenBuilder(Stage stage, Account account, DBAdapter adapter) {
        this.stage = stage;
        this.account = account;
        this.adapter = adapter;
    }

    @Override
    public Region build() {
        GridPane window = new GridPane();

        return window;
    }

}
