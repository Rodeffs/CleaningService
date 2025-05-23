package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Builder;
import javafx.util.Callback;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminCleaningsScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final DBAdapter adapter;
    private final BooleanProperty isAdmin;

    private final TableView<Cleaning> cleaningsTable = new TableView<>();  // the table, once set don't change
    private final ObservableList<Cleaning> cleaningsList = FXCollections.observableArrayList();  // what's actually gonna be changed

    private final BooleanProperty tableScreenVisible = new SimpleBooleanProperty(true);
    private final BooleanProperty tableAddScreenVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty tableEditScreenVisible = new SimpleBooleanProperty(false);

    public AdminCleaningsScreenBuilder(Stage stage, BooleanProperty isAdmin, DBAdapter adapter) {
        this.stage = stage;
        this.isAdmin = isAdmin;
        this.adapter = adapter;
    }

    private void setCleaningsList() {
        if (!isAdmin.getValue()) {
            cleaningsList.clear();
            return;
        }

        try {
            ArrayList<Cleaning> cleanings = adapter.selectCleanings();
            cleaningsList.addAll(cleanings);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setVisibility(CleaningsScreen selectedScreen) {
        tableScreenVisible.set(selectedScreen == CleaningsScreen.TABLE);
        tableAddScreenVisible.set(selectedScreen == CleaningsScreen.ADD);
        tableEditScreenVisible.set(selectedScreen == CleaningsScreen.EDIT);
        cleaningsTable.getSelectionModel().clearSelection();
        cleaningsTable.refresh();
    }

    private void deleteSelected() {
        Cleaning selectedCleaning = cleaningsTable.getSelectionModel().getSelectedItem();

        if (selectedCleaning != null) {
            try {
                adapter.deleteCleaning(selectedCleaning);
                cleaningsList.remove(selectedCleaning);

            } catch (SQLException e) {
                throw new RuntimeException(e);

            } finally {
                cleaningsTable.refresh();
            }
        }
    }

    @Override
    public Region build() {
        BorderPane window = new BorderPane();

        ScrollPane tableScreen = new ScrollPane();
        tableScreen.fitToWidthProperty().set(true);
        tableScreen.fitToHeightProperty().set(true);

        tableScreen.visibleProperty().bind(tableScreenVisible);

        Region tableAddScreen = new AdminCleaningsAddScreenBuilder(stage, adapter, isAdmin, cleaningsList, () -> setVisibility(CleaningsScreen.TABLE)).build();
        tableAddScreen.visibleProperty().bind(tableAddScreenVisible);

        Region tableEditScreen = new AdminCleaningsEditScreenBuilder(stage, adapter, cleaningsTable, () -> setVisibility(CleaningsScreen.TABLE)).build();
        tableEditScreen.visibleProperty().bind(tableEditScreenVisible);

        window.setCenter(new StackPane(tableScreen, tableAddScreen, tableEditScreen));

        TableColumn<Cleaning, Integer> idCol = new TableColumn<>("ID");
        idCol.prefWidthProperty().bind(stage.widthProperty().divide(8));

        idCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Cleaning, Integer> cleaning) {
                return new ReadOnlyObjectWrapper<>(cleaning.getValue().getId());
            }
        });

        TableColumn<Cleaning, String> dateTimeCol = new TableColumn<>("Date & Time");
        dateTimeCol.prefWidthProperty().bind(stage.widthProperty().divide(8));

        dateTimeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cleaning, String> cleaning) {
                return new ReadOnlyObjectWrapper<>(cleaning.getValue().getTimestamp().toString());
            }
        });

        TableColumn<Cleaning, String> addressCol = new TableColumn<>("Address");
        addressCol.prefWidthProperty().bind(stage.widthProperty().divide(8));

        addressCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cleaning, String> cleaning) {
                return new ReadOnlyObjectWrapper<>(cleaning.getValue().getAddress().toString());
            }
        });

        TableColumn<Cleaning, String> clientCol = new TableColumn<>("Client");
        clientCol.prefWidthProperty().bind(stage.widthProperty().divide(8));

        clientCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cleaning, String> cleaning) {
                return new ReadOnlyObjectWrapper<>(cleaning.getValue().getClient().toString());
            }
        });

        TableColumn<Cleaning, String> placeTypeCol = new TableColumn<>("Place Type");
        placeTypeCol.prefWidthProperty().bind(stage.widthProperty().divide(8));

        placeTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cleaning, String> cleaning) {
                return new ReadOnlyObjectWrapper<>(cleaning.getValue().getPlaceType().toString());
            }
        });

        TableColumn<Cleaning, String> cleaningTypeCol = new TableColumn<>("Cleaning Type");
        cleaningTypeCol.prefWidthProperty().bind(stage.widthProperty().divide(8));

        cleaningTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cleaning, String> cleaning) {
                return new ReadOnlyObjectWrapper<>(cleaning.getValue().getCleaningType().toString());
            }
        });

        TableColumn<Cleaning, Integer> cleanersAmountCol = new TableColumn<>("Cleaners Amount");
        cleanersAmountCol.prefWidthProperty().bind(stage.widthProperty().divide(8));

        cleanersAmountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Cleaning, Integer> cleaning) {
                return new ReadOnlyObjectWrapper<>(cleaning.getValue().getCleanersAmount());
            }
        });

        TableColumn<Cleaning, Double> totalPriceCol = new TableColumn<>("Total Price");
        totalPriceCol.prefWidthProperty().bind(stage.widthProperty().divide(8));

        totalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cleaning, Double> cleaning) {
                return new ReadOnlyObjectWrapper<>(cleaning.getValue().getTotalPrice());
            }
        });

        cleaningsTable.getColumns().addAll(idCol, dateTimeCol, addressCol, clientCol, placeTypeCol, cleaningTypeCol, cleanersAmountCol, totalPriceCol);

        cleaningsTable.setItems(cleaningsList);
        cleaningsTable.prefHeightProperty().bind(stage.widthProperty());
        cleaningsTable.prefWidthProperty().bind(stage.heightProperty());

        tableScreen.setContent(cleaningsTable);

        isAdmin.addListener(e -> setCleaningsList());

        Button orderButton = new Button("Order a cleaning");
        orderButton.setOnAction(e -> setVisibility(CleaningsScreen.ADD));

        Button editButton = new Button("Edit ordered cleaning");
        editButton.setOnAction(e -> setVisibility(CleaningsScreen.EDIT));

        Button deleteButton = new Button("Cancel an order");
        deleteButton.setOnAction(e -> deleteSelected());

        HBox controlButtons = new HBox(0);
        controlButtons.getChildren().addAll(orderButton, editButton, deleteButton);
        window.setBottom(controlButtons);

        return window;
    }
}
