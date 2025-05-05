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
import javafx.stage.Stage;
import javafx.util.Builder;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserAccountScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final Account account;
    private final Client client;
    private final DBAdapter adapter;
    private final BooleanProperty isLoggedIn;
    private final BooleanProperty isClient;

    private final BooleanProperty incorrectLoginVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty emptyFieldsVisible = new SimpleBooleanProperty(false);

    private final TextField loginInput = new TextField();
    private final TextField passwordInput = new TextField();
    private final TextField displayNameInput = new TextField();
    private final TextField nameInput = new TextField();
    private final TextField surnameInput = new TextField();
    private final TextField secondNameInput = new TextField();
    private final ArrayList<ClientType> clientTypes = new ArrayList<>();
    private final ComboBox<ClientType> clientTypeInput = new ComboBox<>();
    private final TextField emailInput = new TextField();
    private final TextField phoneInput = new TextField();

    public UserAccountScreenBuilder(Stage stage, Account account, BooleanProperty isLoggedIn, Client client, BooleanProperty isClient, DBAdapter adapter) {
        this.stage = stage;
        this.account = account;
        this.client = client;
        this.isClient = isClient;
        this.isLoggedIn = isLoggedIn;
        this.adapter = adapter;
    }

    private boolean checkInput() {
        boolean inputIsCorrect =
                (loginInput.getText() != null) && !loginInput.getText().isEmpty() &&
                (passwordInput.getText() != null) && !passwordInput.getText().isEmpty() &&
                (displayNameInput.getText() != null) && !displayNameInput.getText().isEmpty() &&
                (surnameInput.getText() != null) && !surnameInput.getText().isEmpty() &&
                (secondNameInput.getText() != null) && !secondNameInput.getText().isEmpty() &&
                (emailInput.getText() != null) && !emailInput.getText().isEmpty() &&
                (phoneInput.getText() != null) && !phoneInput.getText().isEmpty();

        emptyFieldsVisible.set(!inputIsCorrect);

        try {
            if (!loginInput.getText().equals(account.getLogin()) && (adapter.selectAccount(loginInput.getText()) != null)) {
                incorrectLoginVisible.set(true);
                inputIsCorrect = false;
            }
            else
                incorrectLoginVisible.set(false);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return inputIsCorrect;
    }

    private void updateInfo() {
        if (!isLoggedIn.getValue() || !isClient.getValue())
            return;

        if (checkInput()) {
            try {
                String newLogin = loginInput.getText();
                String newPassword = passwordInput.getText();
                String newDisplayName = displayNameInput.getText();
                String newName = nameInput.getText();
                String newSurname = surnameInput.getText();
                String newSecondName = secondNameInput.getText();
                ClientType newClientType = clientTypeInput.getValue();
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
    }

    private void displayInfo() {
        if (!isLoggedIn.getValue() || !isClient.getValue())
            return;

        loginInput.setText(account.getLogin());
        passwordInput.setText(account.getPassword());
        displayNameInput.setText(account.getDisplayName());
        nameInput.setText(client.getName());
        surnameInput.setText(client.getSurname());
        secondNameInput.setText(client.getSecondName());
        clientTypeInput.setValue(client.getType());
        emailInput.setText(client.getEmail());
        phoneInput.setText(client.getPhone());
    }

    @Override
    public Region build() {
        GridPane window = new GridPane();

        window.prefHeightProperty().bind(stage.heightProperty());
        window.prefWidthProperty().bind(stage.widthProperty());
        window.setAlignment(Pos.CENTER);
        window.setHgap(10);
        window.setVgap(10);
        window.setPadding(new Insets(25, 25, 25, 25));

        Text accountInfoText = new Text("Account Info");
        accountInfoText.setFont(Font.font("Verdana", 50));
        window.add(accountInfoText, 0, 0, 2, 1);

        window.add(new Label("Login:"), 0, 1);
        window.add(loginInput, 1, 1);

        Text incorrectLoginText = new Text("This login is already in use");
        incorrectLoginText.setFont(Font.font("Verdana", 20));
        incorrectLoginText.setFill(Color.RED);
        incorrectLoginText.visibleProperty().bind(incorrectLoginVisible);
        window.add(incorrectLoginText, 2, 1);

        window.add(new Label("Password:"), 0, 2);
        window.add(passwordInput, 1, 2);

        window.add(new Label("Display name:"), 0, 3);
        window.add(displayNameInput, 1, 3);

        window.add(new Label("Real name:"), 0, 4);
        window.add(nameInput, 1, 4);

        window.add(new Label("Surname:"), 0, 5);
        window.add(surnameInput, 1, 5);

        window.add(new Label("Second name:"), 0, 6);
        window.add(secondNameInput, 1, 6);

        window.add(new Label("Type:"), 0, 7);

        try {
            clientTypes.addAll(adapter.selectClientTypes());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        clientTypeInput.setItems(FXCollections.observableArrayList(clientTypes));
        window.add(clientTypeInput, 1, 7);

        window.add(new Label("Email:"), 0, 8);
        window.add(emailInput, 1, 8);

        window.add(new Label("Phone:"), 0, 9);
        window.add(phoneInput, 1, 9);

        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> updateInfo());

        HBox saveBox = new HBox(10);
        saveBox.setAlignment(Pos.BOTTOM_LEFT);
        saveBox.getChildren().add(saveButton);
        window.add(saveBox,0, 10);

        Button resetButton = new Button("Reset Changes");
        resetButton.setOnAction(e -> displayInfo());

        HBox resetBox = new HBox(10);
        resetBox.setAlignment(Pos.BOTTOM_RIGHT);
        resetBox.getChildren().add(resetButton);
        window.add(resetBox, 1, 10);

        Text emptyFieldsText = new Text("Some fields are empty!");
        emptyFieldsText.setFont(Font.font("Verdana", 20));
        emptyFieldsText.setFill(Color.RED);
        emptyFieldsText.visibleProperty().bind(emptyFieldsVisible);
        window.add(emptyFieldsText, 0, 11, 1, 2);

        isClient.addListener((ob, oldVal, newVal) -> displayInfo());

        return new ScrollPane(window);
    }

}
