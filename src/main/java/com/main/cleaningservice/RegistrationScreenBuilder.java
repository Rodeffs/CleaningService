package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Builder;

import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationScreenBuilder implements Builder<Region> {
    Account account;
    Runnable exitAuthenticationScreen;
    Runnable switchToLoginScreen;
    DBAdapter adapter;
    BooleanProperty incorrectLoginVisible = new SimpleBooleanProperty(false);
    BooleanProperty incorrectInputVisible = new SimpleBooleanProperty(false);

    public RegistrationScreenBuilder(Account account, DBAdapter adapter, Runnable exitAuthenticationScreen, Runnable switchToLoginScreen) {
        this.account = account;
        this.adapter = adapter;
        this.exitAuthenticationScreen = exitAuthenticationScreen;
        this.switchToLoginScreen = switchToLoginScreen;
    }

    private void createAccountClient(String login, String password, String displayName, String name, String surname, String secondName, String clientType, String email, String phone) {
        try {
            if (adapter.selectAccount(login) != null)
                incorrectLoginVisible.set(true);

            else {
                incorrectLoginVisible.set(false);

                if (login.isEmpty() || password.isEmpty() || displayName.isEmpty() || name.isEmpty() || surname.isEmpty() || secondName.isEmpty() || clientType.isEmpty() || email.isEmpty() || phone.isEmpty())
                    incorrectInputVisible.set(true);

                else {
                    incorrectInputVisible.set(false);

                    adapter.insertAccount(login, password, displayName);
                    account = adapter.selectAccount(login);

                    ClientType type = adapter.selectClientType(clientType);
                    adapter.insertClient(name, surname, secondName, type, account, email, phone);

                    exitAuthenticationScreen.run();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Region build() {
        GridPane window = new GridPane();
        window.setAlignment(Pos.CENTER);
        window.setHgap(10);  // gaps between columns
        window.setVgap(10);  // gaps between rows
        window.setPadding(new Insets(25, 25, 25, 25));  // padding from the edges

        Text registerText = new Text("Create an account");
        registerText.setFont(Font.font("Verdana", 50));
        window.add(registerText, 0, 0, 2, 1);

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

        PasswordField passwordInput = new PasswordField();
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

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> createAccountClient(loginInput.getText(), passwordInput.getText(), displayNameInput.getText(), nameInput.getText(), surnameInput.getText(), secondNameInput.getText(), clientTypeInput.getValue(), emailInput.getText(), phoneInput.getText()));

        HBox registerBox = new HBox(10);
        registerBox.setAlignment(Pos.BOTTOM_LEFT);
        registerBox.getChildren().add(registerButton);
        window.add(registerBox, 0, 10);

        Button logInButton = new Button("Log in existing account");
        logInButton.setOnAction(e -> switchToLoginScreen.run());

        HBox logInBox = new HBox(10);
        logInBox.setAlignment(Pos.BOTTOM_RIGHT);
        logInBox.getChildren().add(logInButton);
        window.add(logInBox, 1, 10);

        Text incorrectInputText = new Text("Error, all fields need to be filled!");
        incorrectInputText.setFont(Font.font("Verdana", 20));
        incorrectInputText.setFill(Color.RED);
        incorrectInputText.visibleProperty().bind(incorrectInputVisible);
        window.add(incorrectInputText, 0, 11, 2, 1);

        return window;
    }
}
