package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Builder;
import javafx.util.Callback;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class UserCleaningsScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final DBAdapter adapter;
    private final Client client;
    private final BooleanProperty isClient;

    private final TableView<Cleaning> cleaningsTable = new TableView<Cleaning>();  // the table, once set don't change
    private final ObservableList<Cleaning> cleaningsList = FXCollections.observableArrayList();  // what's actually gonna be changed

    private final BooleanProperty tableScreenVisible = new SimpleBooleanProperty(true);
    private final BooleanProperty tableAddScreenVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty tableEditScreenVisible = new SimpleBooleanProperty(false);

    public UserCleaningsScreenBuilder(Stage stage, Client client, BooleanProperty isClient, DBAdapter adapter) {
        this.stage = stage;
        this.client = client;
        this.isClient = isClient;
        this.adapter = adapter;
    }

    private void setCleaningsList() {
        if (!isClient.getValue()) {
            cleaningsList.clear();
            return;
        }

        try {
            ArrayList<Cleaning> cleanings = adapter.selectCleanings(client);
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

        Region tableAddScreen = new UserCleaningsAddScreenBuilder(stage, adapter, client, isClient, cleaningsList, () -> setVisibility(CleaningsScreen.TABLE)).build();
        tableAddScreen.visibleProperty().bind(tableAddScreenVisible);

        Region tableEditScreen = new UserCleaningsEditScreenBuilder(stage, adapter, cleaningsTable, () -> setVisibility(CleaningsScreen.TABLE)).build();
        tableEditScreen.visibleProperty().bind(tableEditScreenVisible);

        window.setCenter(new StackPane(tableScreen, tableAddScreen, tableEditScreen));

        TableColumn<Cleaning, Timestamp> dateTimeCol = new TableColumn<Cleaning, Timestamp>("Date & Time");
        dateTimeCol.prefWidthProperty().bind(stage.widthProperty().divide(6));

        // The following code is used to sync the table contents with the Cleaning class
        // Whenever the value Timestamp of class Cleaning is updated it will use call()
        // ReadOnlyObjectWrapper is used to wrap the Timestamp value to make it observable

        dateTimeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Timestamp>, ObservableValue<Timestamp>>() {
            public ObservableValue<Timestamp> call(TableColumn.CellDataFeatures<Cleaning, Timestamp> dateTime) {
                return new ReadOnlyObjectWrapper<Timestamp>(dateTime.getValue().getTimestamp());
            }
        });

        TableColumn<Cleaning, Address> addressCol = new TableColumn<Cleaning, Address>("Address");
        addressCol.prefWidthProperty().bind(stage.widthProperty().divide(6));

        addressCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Address>, ObservableValue<Address>>() {
            public ObservableValue<Address> call(TableColumn.CellDataFeatures<Cleaning, Address> address) {
                return new ReadOnlyObjectWrapper<Address>(address.getValue().getAddress());
            }
        });

        TableColumn<Cleaning, PlaceType> placeTypeCol = new TableColumn<Cleaning, PlaceType>("Place Type");
        placeTypeCol.prefWidthProperty().bind(stage.widthProperty().divide(6));

        placeTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, PlaceType>, ObservableValue<PlaceType>>() {
            public ObservableValue<PlaceType> call(TableColumn.CellDataFeatures<Cleaning, PlaceType> placeType) {
                return new ReadOnlyObjectWrapper<PlaceType>(placeType.getValue().getPlaceType());
            }
        });

        TableColumn<Cleaning, CleaningType> cleaningTypeCol = new TableColumn<Cleaning, CleaningType>("Cleaning Type");
        cleaningTypeCol.prefWidthProperty().bind(stage.widthProperty().divide(6));

        cleaningTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, CleaningType>, ObservableValue<CleaningType>>() {
            public ObservableValue<CleaningType> call(TableColumn.CellDataFeatures<Cleaning, CleaningType> cleaningType) {
                return new ReadOnlyObjectWrapper<CleaningType>(cleaningType.getValue().getCleaningType());
            }
        });

        TableColumn<Cleaning, Integer> cleanersAmountCol = new TableColumn<Cleaning, Integer>("Cleaners Amount");
        cleanersAmountCol.prefWidthProperty().bind(stage.widthProperty().divide(6));

        cleanersAmountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Cleaning, Integer> cleanersAmount) {
                return new ReadOnlyObjectWrapper<Integer>(cleanersAmount.getValue().getCleanersAmount());
            }
        });

        TableColumn<Cleaning, Double> totalPriceCol = new TableColumn<Cleaning, Double>("Total Price");
        totalPriceCol.prefWidthProperty().bind(stage.widthProperty().divide(6));

        totalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cleaning, Double> totalPrice) {
                return new ReadOnlyObjectWrapper<Double>(totalPrice.getValue().getTotalPrice());
            }
        });

        cleaningsTable.getColumns().addAll(dateTimeCol, addressCol, placeTypeCol, cleaningTypeCol, cleanersAmountCol, totalPriceCol);

        cleaningsTable.setItems(cleaningsList);
        cleaningsTable.prefHeightProperty().bind(stage.widthProperty());
        cleaningsTable.prefWidthProperty().bind(stage.heightProperty());

        tableScreen.setContent(cleaningsTable);

        isClient.addListener(e -> setCleaningsList());

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
