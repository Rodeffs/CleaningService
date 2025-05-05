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
    private final DBAdapter adapter;
    private final Client client;
    private final BooleanProperty isClient;

    private final TableView<Cleaning> cleaningsTable = new TableView<Cleaning>();  // the table, once set don't change
    private final ObservableList<Cleaning> cleaningsList = FXCollections.observableArrayList();  // what's actually gonna be changed
    private final Cleaning selectedCleaning = new Cleaning();

    private final BooleanProperty tableScreenVisible = new SimpleBooleanProperty(true);
    private final BooleanProperty tableAddScreenVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty tableEditScreenVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty isSelected = new SimpleBooleanProperty(false);

    public UserCleaningsScreenBuilder(Client client, BooleanProperty isClient, DBAdapter adapter) {
        this.client = client;
        this.isClient = isClient;
        this.adapter = adapter;
    }

    private void setTableColumns() {
        TableColumn<Cleaning, Timestamp> dateTimeCol = new TableColumn<Cleaning, Timestamp>("Date & Time");
        dateTimeCol.setPrefWidth(150);

        // The following code is used to sync the table contents with the Cleaning class
        // Whenever the value Timestamp of class Cleaning is updated it will use call()
        // ReadOnlyObjectWrapper is used to wrap the Timestamp value to make it observable

        dateTimeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Timestamp>, ObservableValue<Timestamp>>() {
            public ObservableValue<Timestamp> call(TableColumn.CellDataFeatures<Cleaning, Timestamp> dateTime) {
                return new ReadOnlyObjectWrapper<Timestamp>(dateTime.getValue().getTimestamp());
            }
        });

        TableColumn<Cleaning, Address> addressCol = new TableColumn<Cleaning, Address>("Address");
        addressCol.setPrefWidth(170);

        addressCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Address>, ObservableValue<Address>>() {
            public ObservableValue<Address> call(TableColumn.CellDataFeatures<Cleaning, Address> address) {
                return new ReadOnlyObjectWrapper<Address>(address.getValue().getAddress());
            }
        });

        TableColumn<Cleaning, PlaceType> placeTypeCol = new TableColumn<Cleaning, PlaceType>("Place Type");
        placeTypeCol.setPrefWidth(100);

        placeTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, PlaceType>, ObservableValue<PlaceType>>() {
            public ObservableValue<PlaceType> call(TableColumn.CellDataFeatures<Cleaning, PlaceType> placeType) {
                return new ReadOnlyObjectWrapper<PlaceType>(placeType.getValue().getPlaceType());
            }
        });

        TableColumn<Cleaning, CleaningType> cleaningTypeCol = new TableColumn<Cleaning, CleaningType>("Cleaning Type");
        cleaningTypeCol.setPrefWidth(100);

        cleaningTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, CleaningType>, ObservableValue<CleaningType>>() {
            public ObservableValue<CleaningType> call(TableColumn.CellDataFeatures<Cleaning, CleaningType> cleaningType) {
                return new ReadOnlyObjectWrapper<CleaningType>(cleaningType.getValue().getCleaningType());
            }
        });

        TableColumn<Cleaning, Integer> cleanersAmountCol = new TableColumn<Cleaning, Integer>("Cleaners Amount");
        cleanersAmountCol.setPrefWidth(120);

        cleanersAmountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Cleaning, Integer> cleanersAmount) {
                return new ReadOnlyObjectWrapper<Integer>(cleanersAmount.getValue().getCleanersAmount());
            }
        });

        TableColumn<Cleaning, Double> totalPriceCol = new TableColumn<Cleaning, Double>("Total Price");
        totalPriceCol.setPrefWidth(100);

        totalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cleaning, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Cleaning, Double> totalPrice) {
                return new ReadOnlyObjectWrapper<Double>(totalPrice.getValue().getTotalPrice());
            }
        });

        cleaningsTable.getColumns().addAll(dateTimeCol, addressCol, placeTypeCol, cleaningTypeCol, cleanersAmountCol, totalPriceCol);
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

    private void setVisibility(UserCleaningsScreen selectedScreen) {
        tableScreenVisible.set(selectedScreen == UserCleaningsScreen.TABLE);
        tableAddScreenVisible.set(selectedScreen == UserCleaningsScreen.ADD);
        tableEditScreenVisible.set(selectedScreen == UserCleaningsScreen.EDIT);
    }

    private void selectCleaning() {
        Cleaning newSelected = cleaningsTable.getSelectionModel().getSelectedItem();

        if (newSelected != null) {
            selectedCleaning.setCleaning(newSelected);
            isSelected.set(true);
        }
        else {
            selectedCleaning.clear();
            isSelected.set(false);
        }
    }

    private void beginEditing() {
        if (isSelected.getValue())
            setVisibility(UserCleaningsScreen.EDIT);
    }

    private void deleteSelected() {
        Cleaning unselected = new Cleaning();

        if (!selectedCleaning.equals(unselected)) {
            try {
                adapter.deleteCleaning(selectedCleaning);
                cleaningsList.remove(selectedCleaning);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Region build() {
        BorderPane window = new BorderPane();

        ScrollPane tableScreen = new ScrollPane();
        tableScreen.visibleProperty().bind(tableScreenVisible);

        Region tableAddScreen = new UserCleaningsAddScreenBuilder(adapter, client, isClient, cleaningsList, () -> setVisibility(UserCleaningsScreen.TABLE)).build();
        tableAddScreen.visibleProperty().bind(tableAddScreenVisible);

        Region tableEditScreen = new UserCleaningsEditScreenBuilder(adapter, selectedCleaning, isSelected, () -> setVisibility(UserCleaningsScreen.TABLE)).build();
        tableEditScreen.visibleProperty().bind(tableEditScreenVisible);

        window.setCenter(new StackPane(tableScreen, tableAddScreen, tableEditScreen));

        setTableColumns();
        cleaningsTable.setItems(cleaningsList);
        tableScreen.setContent(cleaningsTable);

        cleaningsTable.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> selectCleaning());

        isClient.addListener(e -> setCleaningsList());

        Button orderButton = new Button("Order a cleaning");
        orderButton.setOnAction(e -> setVisibility(UserCleaningsScreen.ADD));

        Button editButton = new Button("Edit ordered cleaning");
        editButton.setOnAction(e -> beginEditing());

        Button deleteButton = new Button("Cancel an order");
        deleteButton.setOnAction(e -> deleteSelected());

        HBox controlButtons = new HBox(0);
        controlButtons.getChildren().addAll(orderButton, editButton, deleteButton);
        window.setBottom(controlButtons);

        return window;
    }

}
