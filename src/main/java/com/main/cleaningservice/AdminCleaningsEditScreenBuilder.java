package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Builder;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdminCleaningsEditScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final DBAdapter adapter;
    private final Cleaning selectedCleaning = new Cleaning();
    private final Runnable exitScreen;
    private final TableView<Cleaning> cleaningsTable;

    private final Country selectCountryPrompt = new Country(-1, "Select Country");
    private final ObservableList<Country> countryList = FXCollections.observableArrayList();
    private final City selectCityPrompt = new City(-1, selectCountryPrompt, "Select City");
    private final ObservableList<City> cityList = FXCollections.observableArrayList();
    private final Street selectStreetPrompt = new Street(-1, selectCityPrompt, "Select Street");
    private final ObservableList<Street> streetList = FXCollections.observableArrayList();
    private final PlaceType selectPlaceTypePrompt = new PlaceType(-1, "Select Place Type");
    private final ObservableList<PlaceType> placeTypeList = FXCollections.observableArrayList();
    private final CleaningType selectCleaningTypePrompt = new CleaningType(-1, "Select Cleaning Type");
    private final ObservableList<CleaningType> cleaningTypeList = FXCollections.observableArrayList();
    private final ObservableList<Service> serviceList = FXCollections.observableArrayList();
    private final ObservableList<Client> clientList = FXCollections.observableArrayList();


    private final TextField dateTimeInput = new TextField();
    private final ComboBox<Country> countryInput = new ComboBox<>(countryList);
    private final ComboBox<City> cityInput = new ComboBox<>(cityList);
    private final ComboBox<Street> streetInput = new ComboBox<>(streetList);
    private final TextField buildingInput = new TextField();
    private final TextField entranceInput = new TextField();
    private final TextField floorInput = new TextField();
    private final TextField unitInput = new TextField();
    private final ComboBox<PlaceType> placeInput = new ComboBox<>(placeTypeList);
    private final ComboBox<CleaningType> typeInput = new ComboBox<>(cleaningTypeList);
    private final ListView<Service> serviceSelection = new ListView<>(serviceList);
    private final ListView<Client> clientSelection = new ListView<>(clientList);
    private final TextField totalPriceOutput = new TextField();

    private final BooleanProperty incorrectInputVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty incorrectDateTimeVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty incorrectBuildingVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty incorrectEntranceVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty incorrectFloorVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty incorrectUnitVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty incorrectServicesVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty incorrectClientVisible = new SimpleBooleanProperty(false);

    private final ArrayList<Service> previouslySelectedServices = new ArrayList<>();

    public AdminCleaningsEditScreenBuilder(Stage stage, DBAdapter adapter, TableView<Cleaning> cleaningsTable, Runnable exitScreen) {
        this.stage = stage;
        this.adapter = adapter;
        this.cleaningsTable = cleaningsTable;
        this.exitScreen = exitScreen;
    }

    private void setData() {
        if (cleaningsTable.getSelectionModel().getSelectedItem() == null)
            return;

        selectedCleaning.set(cleaningsTable.getSelectionModel().getSelectedItem());

        try {
            countryList.clear();
            countryList.add(selectCountryPrompt);
            countryList.addAll(adapter.selectCountries());
            placeTypeList.clear();
            placeTypeList.add(selectPlaceTypePrompt);
            placeTypeList.addAll(adapter.selectPlaceTypes());
            cleaningTypeList.clear();
            cleaningTypeList.add(selectCleaningTypePrompt);
            cleaningTypeList.addAll(adapter.selectCleaningTypes());
            serviceList.clear();
            serviceList.addAll(adapter.selectServices());
            clientList.clear();
            clientList.addAll(adapter.selectClients());
            clientSelection.getSelectionModel().select(selectedCleaning.getClient());

            dateTimeInput.setText(selectedCleaning.getTimestamp().toString());
            countryInput.setValue(selectedCleaning.getAddress().getStreet().getCity().getCountry());
            cityInput.setValue(selectedCleaning.getAddress().getStreet().getCity());
            streetInput.setValue(selectedCleaning.getAddress().getStreet());
            buildingInput.setText(Integer.toString(selectedCleaning.getAddress().getBuildingNumber()));
            entranceInput.setText(Integer.toString(selectedCleaning.getAddress().getEntranceNumber()));
            floorInput.setText(Integer.toString(selectedCleaning.getAddress().getFloorNumber()));
            unitInput.setText(Integer.toString(selectedCleaning.getAddress().getUnitNumber()));
            placeInput.setValue(selectedCleaning.getPlaceType());
            typeInput.setValue(selectedCleaning.getCleaningType());
            totalPriceOutput.setText("0.0");
            previouslySelectedServices.clear();
            previouslySelectedServices.addAll(adapter.selectCleaningServices(selectedCleaning));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void selectCountry() {
        Country selectedCountry = countryInput.getSelectionModel().getSelectedItem();

        cityList.clear();
        cityList.add(selectCityPrompt);
        cityInput.setValue(selectCityPrompt);

        streetList.clear();
        streetList.add(selectStreetPrompt);
        streetInput.setValue(selectStreetPrompt);

        if (!(selectedCountry == null) && !selectedCountry.equals(selectCountryPrompt)) {
            try {
                cityList.addAll(adapter.selectCities(selectedCountry));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void selectCity() {
        City selectedCity = cityInput.getSelectionModel().getSelectedItem();

        streetList.clear();
        streetList.add(selectStreetPrompt);
        streetInput.setValue(selectStreetPrompt);

        if (!(selectedCity == null) && !selectedCity.equals(selectCityPrompt)) {
            try {
                streetList.addAll(adapter.selectStreets(selectedCity));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void calculatePrice() {
        ObservableList<Service> selectedServices = serviceSelection.getSelectionModel().getSelectedItems();

        double totalPrice = 0.0;

        for (Service service : selectedServices)
            if (service != null)
                totalPrice += service.getPrice();

        totalPriceOutput.setText(Double.toString(totalPrice));
    }

    private void resetDataAndQuit() {
        selectedCleaning.clear();
        dateTimeInput.clear();
        countryList.clear();
        countryList.add(selectCountryPrompt);
        countryInput.setValue(selectCountryPrompt);
        cityList.clear();
        cityList.add(selectCityPrompt);
        cityInput.setValue(selectCityPrompt);
        streetList.clear();
        streetList.add(selectStreetPrompt);
        streetInput.setValue(selectStreetPrompt);
        buildingInput.clear();
        entranceInput.clear();
        floorInput.clear();
        unitInput.clear();
        serviceSelection.getSelectionModel().clearSelection();
        clientSelection.getSelectionModel().clearSelection();
        totalPriceOutput.setText("0.0");
        incorrectInputVisible.set(false);
        incorrectDateTimeVisible.set(false);
        incorrectBuildingVisible.set(false);
        incorrectEntranceVisible.set(false);
        incorrectFloorVisible.set(false);
        incorrectUnitVisible.set(false);
        incorrectServicesVisible.set(false);
        previouslySelectedServices.clear();
        placeTypeList.clear();
        placeTypeList.add(selectPlaceTypePrompt);
        placeInput.setValue(selectPlaceTypePrompt);
        cleaningTypeList.clear();
        cleaningTypeList.add(selectCleaningTypePrompt);
        typeInput.setValue(selectCleaningTypePrompt);
        serviceList.clear();
        clientList.clear();
        exitScreen.run();
    }

    private boolean checkValidity() {
        boolean inputIsCorrect = true;

        // Checking date and time

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsed = dateFormat.parse(dateTimeInput.getText());
            Timestamp dateTime = new Timestamp(parsed.getTime());
            incorrectDateTimeVisible.set(false);

        } catch (ParseException e) {
            incorrectDateTimeVisible.set(true);
            inputIsCorrect = false;
        }

        // Checking selectable values

        Country country = countryInput.getSelectionModel().getSelectedItem();
        City city = cityInput.getSelectionModel().getSelectedItem();
        Street street = streetInput.getSelectionModel().getSelectedItem();
        PlaceType placeType = placeInput.getSelectionModel().getSelectedItem();
        CleaningType cleaningType = typeInput.getSelectionModel().getSelectedItem();

        if ((country.equals(selectCountryPrompt)) || (city.equals(selectCityPrompt)) || (street.equals(selectStreetPrompt)) || (placeType.equals(selectPlaceTypePrompt)) || (cleaningType.equals(selectCleaningTypePrompt))) {
            incorrectInputVisible.set(true);
            inputIsCorrect = false;
        }
        else
            incorrectInputVisible.set(false);

        // Checking building number

        try {
            int buildingNumber = Integer.parseInt(buildingInput.getText());

            if ((buildingNumber < 0) || (buildingNumber > 1000)) {
                incorrectBuildingVisible.set(true);
                inputIsCorrect = false;
            }
            else
                incorrectBuildingVisible.set(false);

        } catch (NumberFormatException e) {
            incorrectBuildingVisible.set(true);
            inputIsCorrect = false;
        }

        // Checking entrance number

        try {
            int entranceNumber = Integer.parseInt(entranceInput.getText());

            if ((entranceNumber < 0) || (entranceNumber > 1000)) {
                incorrectEntranceVisible.set(true);
                inputIsCorrect = false;
            }
            else
                incorrectEntranceVisible.set(false);

        } catch (NumberFormatException e) {
            incorrectEntranceVisible.set(true);
            inputIsCorrect = false;
        }

        // Checking floor number

        try {
            int floorNumber = Integer.parseInt(floorInput.getText());

            if ((floorNumber < -1000) || (floorNumber > 1000)) {
                incorrectFloorVisible.set(true);
                inputIsCorrect = false;
            }
            else
                incorrectFloorVisible.set(false);

        } catch (NumberFormatException e) {
            incorrectFloorVisible.set(true);
            inputIsCorrect = false;
        }

        // Checking unit number

        try {
            int unitNumber = Integer.parseInt(unitInput.getText());

            if ((unitNumber < 0) || (unitNumber > 10000)) {
                incorrectUnitVisible.set(true);
                inputIsCorrect = false;
            }
            else
                incorrectUnitVisible.set(false);

        } catch (NumberFormatException e) {
            incorrectUnitVisible.set(true);
            inputIsCorrect = false;
        }

        // Checking selected services

        ObservableList<Service> services = serviceSelection.getSelectionModel().getSelectedItems();

        if ((services.isEmpty())) {
            incorrectServicesVisible.set(true);
            inputIsCorrect = false;
        }
        else
            incorrectServicesVisible.set(false);

        // Checking selected client

        Client client = clientSelection.getSelectionModel().getSelectedItem();

        if (client == null) {
            incorrectClientVisible.set(true);
            inputIsCorrect = false;
        }
        else
            incorrectClientVisible.set(false);

        return inputIsCorrect;
    }

    private void edit() {
        if (checkValidity()) {
            Timestamp newDateTime = null;
            int newBuildingNumber = Integer.parseInt(buildingInput.getText());
            int newEntranceNumber = Integer.parseInt(entranceInput.getText());
            int newFloorNumber = Integer.parseInt(floorInput.getText());
            int newUnitNumber = Integer.parseInt(unitInput.getText());
            Street newStreet = streetInput.getSelectionModel().getSelectedItem();
            PlaceType newPlaceType = placeInput.getSelectionModel().getSelectedItem();
            CleaningType newCleaningType = typeInput.getSelectionModel().getSelectedItem();
            ObservableList<Service> newServices = serviceSelection.getSelectionModel().getSelectedItems();
            Client newClient = clientSelection.getSelectionModel().getSelectedItem();
            double newTotalPrice = Double.parseDouble(totalPriceOutput.getText());

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsed = dateFormat.parse(dateTimeInput.getText());
                newDateTime = new Timestamp(parsed.getTime());

            } catch (ParseException e) {}

            try {
                if (!newDateTime.equals(selectedCleaning.getTimestamp()))
                    adapter.updateCleaningTimestamp(selectedCleaning, newDateTime);

                if ((!newStreet.equals(selectedCleaning.getAddress().getStreet())) || (newBuildingNumber != selectedCleaning.getAddress().getBuildingNumber()) || (newEntranceNumber != selectedCleaning.getAddress().getEntranceNumber()) || (newFloorNumber != selectedCleaning.getAddress().getFloorNumber()) || (newUnitNumber != selectedCleaning.getAddress().getUnitNumber())) {
                    Address newAddress = adapter.selectAddress(newStreet, newBuildingNumber, newEntranceNumber, newFloorNumber, newUnitNumber);

                    if (newAddress == null) {
                        adapter.insertAddress(newStreet, newBuildingNumber, newEntranceNumber, newFloorNumber, newUnitNumber);
                        newAddress = adapter.selectAddress(newStreet, newBuildingNumber, newEntranceNumber, newFloorNumber, newUnitNumber);
                    }

                    adapter.updateCleaningAddress(selectedCleaning, newAddress);
                }

                if (!newClient.equals(selectedCleaning.getClient()))
                    adapter.updateCleaningClient(selectedCleaning, newClient);

                if (!newPlaceType.equals(selectedCleaning.getPlaceType()))
                    adapter.updateCleaningPlaceType(selectedCleaning, newPlaceType);

                if (!newCleaningType.equals(selectedCleaning.getCleaningType()))
                    adapter.updateCleaningType(selectedCleaning, newCleaningType);

                // If the new service wasn't ordered previously, then insert it

                for (Service newService : newServices)
                    if (!previouslySelectedServices.remove(newService))
                        adapter.insertCleaningService(selectedCleaning, newService);

                // The remaining services are all cancelled and therefore deleted

                for (Service cancelledService : previouslySelectedServices)
                    adapter.deleteCleaningService(selectedCleaning, cancelledService);

                if (newTotalPrice != selectedCleaning.getTotalPrice())
                    adapter.updateCleaningTotalPrice(selectedCleaning, newTotalPrice);

            } catch (SQLException e) {
                throw new RuntimeException(e);

            } finally {
                resetDataAndQuit();
            }
        }
    }

    @Override
    public Region build() {
        GridPane window = new GridPane();
        window.prefWidthProperty().bind(stage.widthProperty());
        window.prefHeightProperty().bind(stage.heightProperty());
        window.setAlignment(Pos.CENTER);
        window.setVgap(10);
        window.setHgap(10);
        window.setPadding(new Insets(25, 25, 25, 25));

        cleaningsTable.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> setData());

        Text orderText = new Text("Edit a cleaning");
        orderText.setFont(Font.font("Verdana", 50));
        window.add(orderText, 0, 0, 2, 1);

        window.add(new Label("Date & Time:"), 0, 1);
        window.add(dateTimeInput, 1, 1);

        Text incorrectDateTimeText = new Text("Incorrect date and time input!");
        incorrectDateTimeText.setFont(Font.font("Verdana", 20));
        incorrectDateTimeText.setFill(Color.RED);
        incorrectDateTimeText.visibleProperty().bind(incorrectDateTimeVisible);
        window.add(incorrectDateTimeText, 2, 1);

        countryInput.setValue(selectCountryPrompt);
        countryInput.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> selectCountry());

        window.add(new Label("Country:"), 0, 2);
        window.add(countryInput, 1, 2);

        cityInput.setValue(selectCityPrompt);
        cityInput.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> selectCity());

        window.add(new Label("City:"), 0, 3);
        window.add(cityInput, 1, 3);

        streetInput.setValue(selectStreetPrompt);

        window.add(new Label("Street:"), 0, 4);
        window.add(streetInput, 1, 4);

        window.add(new Label("Building Number:"), 0, 5);
        window.add(buildingInput, 1, 5);

        Text incorrectBuildingText = new Text("Incorrect building number!");
        incorrectBuildingText.setFont(Font.font("Verdana", 20));
        incorrectBuildingText.setFill(Color.RED);
        incorrectBuildingText.visibleProperty().bind(incorrectBuildingVisible);
        window.add(incorrectBuildingText, 2, 5);

        window.add(new Label("Entrance Number:"), 0, 6);
        window.add(entranceInput, 1, 6);

        Text incorrectEntranceText = new Text("Incorrect entrance number!");
        incorrectEntranceText.setFont(Font.font("Verdana", 20));
        incorrectEntranceText.setFill(Color.RED);
        incorrectEntranceText.visibleProperty().bind(incorrectEntranceVisible);
        window.add(incorrectEntranceText, 2, 6);

        window.add(new Label("Floor Number:"), 0, 7);
        window.add(floorInput, 1, 7);

        Text incorrectFloorText = new Text("Incorrect floor number!");
        incorrectFloorText.setFont(Font.font("Verdana", 20));
        incorrectFloorText.setFill(Color.RED);
        incorrectFloorText.visibleProperty().bind(incorrectFloorVisible);
        window.add(incorrectFloorText, 2, 7);

        window.add(new Label("Unit Number:"), 0, 8);
        window.add(unitInput, 1, 8);

        Text incorrectUnitText = new Text("Incorrect unit number!");
        incorrectUnitText.setFont(Font.font("Verdana", 20));
        incorrectUnitText.setFill(Color.RED);
        incorrectUnitText.visibleProperty().bind(incorrectUnitVisible);
        window.add(incorrectUnitText, 2, 8);

        placeInput.setValue(selectPlaceTypePrompt);

        window.add(new Label("Place Type:"), 0, 9);
        window.add(placeInput, 1, 9);

        typeInput.setValue(selectCleaningTypePrompt);

        window.add(new Label("Cleaning Type:"), 0, 10);
        window.add(typeInput, 1, 10);

        window.add(new Label("Select Services:"), 0, 11);
        window.add(serviceSelection, 1, 11);

        serviceSelection.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        serviceSelection.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> calculatePrice());

        Text incorrectServicesText = new Text("You must select at least one service!");
        incorrectServicesText.setFont(Font.font("Verdana", 20));
        incorrectServicesText.setFill(Color.RED);
        incorrectServicesText.visibleProperty().bind(incorrectServicesVisible);
        window.add(incorrectServicesText, 2, 11);

        window.add(new Label("Select Client:"), 0, 12);
        window.add(clientSelection, 1, 12);

        Text incorrectClientText = new Text("You must select a client!");
        incorrectClientText.setFont(Font.font("Verdana", 20));
        incorrectClientText.setFill(Color.RED);
        incorrectClientText.visibleProperty().bind(incorrectClientVisible);
        window.add(incorrectClientText, 2, 12);

        window.add(new Label("Total price:"), 0, 13);
        totalPriceOutput.setEditable(false);
        window.add(totalPriceOutput, 1, 13);

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> edit());

        HBox editBox = new HBox(10);
        editBox.setAlignment(Pos.BOTTOM_LEFT);
        editBox.getChildren().add(editButton);
        window.add(editBox, 0, 14);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> resetDataAndQuit());

        HBox cancelBox = new HBox(10);
        cancelBox.setAlignment(Pos.BOTTOM_RIGHT);
        cancelBox.getChildren().add(cancelButton);
        window.add(cancelBox, 1, 14);

        Text incorrectInputText = new Text("Some selectable fields are empty!");
        incorrectInputText.setFont(Font.font("Verdana", 20));
        incorrectInputText.setFill(Color.RED);
        incorrectInputText.visibleProperty().bind(incorrectInputVisible);
        window.add(incorrectInputText, 0, 15, 2, 1);

        return new ScrollPane(window);
    }
}
