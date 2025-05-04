package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Builder;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserAccountScreenBuilder implements Builder<Region> {
    private final Account account;
    private final Client client;
    private final DBAdapter adapter;
    private final BooleanProperty isLoggedIn;
    private final BooleanProperty isClient;

    private final BooleanProperty incorrectLoginVisible = new SimpleBooleanProperty(false);

    public UserAccountScreenBuilder(Account account, BooleanProperty isLoggedIn, Client client, BooleanProperty isClient, DBAdapter adapter) {
        this.account = account;
        this.client = client;
        this.isClient = isClient;
        this.isLoggedIn = isLoggedIn;
        this.adapter = adapter;
    }

    private void updateInfo(TextField loginInput, TextField passwordInput, TextField displayNameInput, TextField nameInput, TextField surnameInput, TextField secondNameInput, ComboBox<String> clientTypeInput, TextField emailInput, TextField phoneInput) {
        if (!isLoggedIn.getValue() || !isClient.getValue())
            return;

        String newLogin = loginInput.getText();

        try {

            if ((adapter.selectAccount(newLogin) != null) && !newLogin.equals(account.getLogin())) {
                incorrectLoginVisible.set(true);
                return;
            }

            incorrectLoginVisible.set(false);

            String newPassword = passwordInput.getText();
            String newDisplayName = displayNameInput.getText();
            String newName = nameInput.getText();
            String newSurname = surnameInput.getText();
            String newSecondName = secondNameInput.getText();
            ClientType newClientType = adapter.selectClientType(clientTypeInput.getValue());
            String newEmail = emailInput.getText();
            String newPhone = phoneInput.getText();

            if (!newLogin.equals(account.getLogin()))
                adapter.updateAccountLogin(account, newLogin);

            if (!newPassword.equals(account.getPassword()))
                adapter.updateAccountPassword(account, newPassword);

            if (!newDisplayName.equals(account.getDisplayName()))
                adapter.updateAccountDisplayName(account, newDisplayName);

            if (!newName.equals(client.getName()))
                adapter.updateClientName(client, newName);

            if (!newSurname.equals(client.getSurname()))
                adapter.updateClientSurname(client, newSurname);

            if (!newSecondName.equals(client.getSecondName()))
                adapter.updateClientSecondName(client, newSecondName);

            if (!newClientType.equals(client.getType()))
                adapter.updateClientType(client, newClientType);

            if (!newEmail.equals(client.getEmail()))
                adapter.updateClientEmail(client, newEmail);

            if (!newPhone.equals(client.getPhone()))
                adapter.updateClientPhone(client, newPhone);

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private void displayInfo(TextField loginInput, TextField passwordInput, TextField displayNameInput, TextField nameInput, TextField surnameInput, TextField secondNameInput, ComboBox<String> clientTypeInput, TextField emailInput, TextField phoneInput) {
        if (!isLoggedIn.getValue() || !isClient.getValue())
            return;

        loginInput.setText(account.getLogin());
        passwordInput.setText(account.getPassword());
        displayNameInput.setText(account.getDisplayName());
        nameInput.setText(client.getName());
        surnameInput.setText(client.getSurname());
        secondNameInput.setText(client.getSecondName());
        clientTypeInput.setValue(client.getType().getName());
        emailInput.setText(client.getEmail());
        phoneInput.setText(client.getPhone());
    }

    @Override
    public Region build() {
        GridPane window = new GridPane();

        window.setAlignment(Pos.CENTER);
        window.setHgap(10);
        window.setVgap(10);
        window.setPadding(new Insets(25, 25, 25, 25));

        Text accountInfoText = new Text("Account Info");
        accountInfoText.setFont(Font.font("Verdana", 50));
        window.add(accountInfoText, 0, 0, 2, 1);

        Label loginLabel = new Label("Login:");
        window.add(loginLabel, 0, 1);

        TextField loginInput = new TextField();
        window.add(loginInput, 1, 1);

        Text incorrectLoginText = new Text("This login is already in use");
        incorrectLoginText.setFont(Font.font("Verdana", 20));
        incorrectLoginText.setFill(Color.RED);
        incorrectLoginText.visibleProperty().bind(incorrectLoginVisible);
        window.add(incorrectLoginText, 2, 1);

        Label passwordLabel = new Label("Password:");
        window.add(passwordLabel, 0, 2);

        TextField passwordInput = new TextField();
        window.add(passwordInput, 1, 2);

        Label displayNameLabel = new Label("Display name:");
        window.add(displayNameLabel, 0, 3);

        TextField displayNameInput = new TextField();
        window.add(displayNameInput, 1, 3);

        Label nameLabel = new Label("Real name:");
        window.add(nameLabel, 0, 4);

        TextField nameInput = new TextField();
        window.add(nameInput, 1, 4);

        Label surnameLabel = new Label("Surname:");
        window.add(surnameLabel, 0, 5);

        TextField surnameInput = new TextField();
        window.add(surnameInput, 1, 5);

        Label secondNameLabel = new Label("Second name:");
        window.add(secondNameLabel, 0, 6);

        TextField secondNameInput = new TextField();
        window.add(secondNameInput, 1, 6);

        Label clientTypeLabel = new Label("Type:");
        window.add(clientTypeLabel, 0, 7);

        ArrayList<String> clientTypes = new ArrayList<String>();

        try {
            for (ClientType clientType : adapter.selectClientTypes())
                clientTypes.add(clientType.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ComboBox<String> clientTypeInput = new ComboBox<String>(FXCollections.observableArrayList(clientTypes));
        window.add(clientTypeInput, 1, 7);

        Label emailLabel = new Label("Email:");
        window.add(emailLabel, 0, 8);

        TextField emailInput = new TextField();
        window.add(emailInput, 1, 8);

        Label phoneLabel = new Label("Phone:");
        window.add(phoneLabel, 0, 9);

        TextField phoneInput = new TextField();
        window.add(phoneInput, 1, 9);

        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> updateInfo(loginInput, passwordInput, displayNameInput, nameInput, surnameInput, secondNameInput, clientTypeInput, emailInput, phoneInput));

        HBox saveBox = new HBox(10);
        saveBox.setAlignment(Pos.BOTTOM_LEFT);
        saveBox.getChildren().add(saveButton);
        window.add(saveBox,0, 10);

        Button resetButton = new Button("Reset Changes");
        resetButton.setOnAction(e -> displayInfo(loginInput, passwordInput, displayNameInput, nameInput, surnameInput, secondNameInput, clientTypeInput, emailInput, phoneInput));

        HBox resetBox = new HBox(10);
        resetBox.setAlignment(Pos.BOTTOM_RIGHT);
        resetBox.getChildren().add(resetButton);
        window.add(resetBox, 1, 10);

        isClient.addListener((ob, oldVal, newVal) -> displayInfo(loginInput, passwordInput, displayNameInput, nameInput, surnameInput, secondNameInput, clientTypeInput, emailInput, phoneInput));

        return new ScrollPane(window);
    }

}
