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
import javafx.util.Builder;
import javafx.util.Callback;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class UserCleaningsScreenBuilder implements Builder<Region> {
    private final Account account;
    private final BooleanProperty isLoggedIn;
    private final DBAdapter adapter;
    private final Client client;
    private final BooleanProperty isClient;

    private final TableView<Cleaning> cleaningsTable = new TableView<Cleaning>();
    private final ObservableList<Cleaning> cleaningsList = FXCollections.observableArrayList();

    private final BooleanProperty tableScreenVisible = new SimpleBooleanProperty(true);
    private final BooleanProperty tableAddScreenVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty tableEditScreenVisible = new SimpleBooleanProperty(false);

    public UserCleaningsScreenBuilder(Account account, BooleanProperty isLoggedIn, Client client, BooleanProperty isClient, DBAdapter adapter) {
        this.account = account;
        this.isLoggedIn = isLoggedIn;
        this.client = client;
        this.isClient = isClient;
        this.adapter = adapter;
    }

    private void setTableColumns() {
        TableColumn<Cleaning, Timestamp> dateTimeCol = new TableColumn<Cleaning, Timestamp>("Date & Time");

        // The following code is used to sync the table contents with the Cleaning class
        // Whenever the value Timestamp of class Cleaning is updated it will use call()
        // ReadOnlyObjectWrapper is used to wrap the Timestamp value to make it observable

        dateTimeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Timestamp>, ObservableValue<Timestamp>>() {
            public ObservableValue<Timestamp> call(TableColumn.CellDataFeatures<Cleaning, Timestamp> dateTime) {
                return new ReadOnlyObjectWrapper<Timestamp>(dateTime.getValue().getTimestamp());
            }
        });

        TableColumn<Cleaning, Address> addressCol = new TableColumn<Cleaning, Address>("Address");

        addressCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Address>, ObservableValue<Address>>() {
            public ObservableValue<Address> call(TableColumn.CellDataFeatures<Cleaning, Address> address) {
                return new ReadOnlyObjectWrapper<Address>(address.getValue().getAddress());
            }
        });

        TableColumn<Cleaning, PlaceType> placeTypeCol = new TableColumn<Cleaning, PlaceType>("Place Type");

        placeTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, PlaceType>, ObservableValue<PlaceType>>() {
            public ObservableValue<PlaceType> call(TableColumn.CellDataFeatures<Cleaning, PlaceType> placeType) {
                return new ReadOnlyObjectWrapper<PlaceType>(placeType.getValue().getPlaceType());
            }
        });

        TableColumn<Cleaning, CleaningType> cleaningTypeCol = new TableColumn<Cleaning, CleaningType>("Cleaning Type");

        cleaningTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, CleaningType>, ObservableValue<CleaningType>>() {
            public ObservableValue<CleaningType> call(TableColumn.CellDataFeatures<Cleaning, CleaningType> cleaningType) {
                return new ReadOnlyObjectWrapper<CleaningType>(cleaningType.getValue().getCleaningType());
            }
        });

        TableColumn<Cleaning, Integer> cleanersAmountCol = new TableColumn<Cleaning, Integer>("Cleaners Amount");

        cleanersAmountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Cleaning, Integer> cleanersAmount) {
                return new ReadOnlyObjectWrapper<Integer>(cleanersAmount.getValue().getCleanersAmount());
            }
        });

        TableColumn<Cleaning, Double> totalPriceCol = new TableColumn<Cleaning, Double>("Total Price");

        totalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cleaning, Double> totalPrice) {
                return new ReadOnlyObjectWrapper<Double>(totalPrice.getValue().getTotalPrice());
            }
        });

        cleaningsTable.getColumns().addAll(dateTimeCol, addressCol, placeTypeCol, cleaningTypeCol, cleanersAmountCol, totalPriceCol);
    }

    private void setCleaningsList() {
        if (!isLoggedIn.getValue() || !isClient.getValue()) {
            cleaningsList.clear();
            return;
        }

        try {
            ArrayList<Cleaning> cleanings = adapter.selectCleanings(client);
            System.out.println(cleanings.size());
            cleaningsList.addAll(cleanings);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Region build() {
        BorderPane window = new BorderPane();

        ScrollPane tableScreen = new ScrollPane();
        tableScreen.visibleProperty().bind(tableScreenVisible);

        ScrollPane tableAddScreen = new ScrollPane();
        tableAddScreen.visibleProperty().bind(tableAddScreenVisible);

        ScrollPane tableEditScreen = new ScrollPane();
        tableEditScreen.visibleProperty().bind(tableEditScreenVisible);

        window.setCenter(new StackPane(tableScreen, tableAddScreen, tableEditScreen));

        setTableColumns();
        cleaningsTable.setItems(cleaningsList);
        tableScreen.setContent(cleaningsTable);

        isClient.addListener(e -> setCleaningsList());

        Button orderButton = new Button("Order a cleaning");

        Button editButton = new Button("Edit ordered cleaning");

        Button deleteButton = new Button("Cancel an order");

        HBox controlButtons = new HBox(0);
        controlButtons.getChildren().addAll(orderButton, editButton, deleteButton);
        window.setBottom(controlButtons);

        return window;
    }

}
