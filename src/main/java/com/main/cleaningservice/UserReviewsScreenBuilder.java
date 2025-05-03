package com.main.cleaningservice;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class UserReviewsScreenBuilder implements Builder<Region> {
    private final Account account;
    private final DBAdapter adapter;

    public UserReviewsScreenBuilder(Account account, DBAdapter adapter) {
        this.account = account;
        this.adapter = adapter;
    }

    @Override
    public Region build() {
        GridPane window = new GridPane();

        return window;
    }

}
