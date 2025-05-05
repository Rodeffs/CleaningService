package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

public class AdminAccountScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final Account account;
    private final BooleanProperty isLoggedIn;
    private final BooleanProperty isAdmin;
    private final DBAdapter adapter;

    private final BooleanProperty incorrectLoginVisible = new SimpleBooleanProperty(false);
    private final BooleanProperty emptyFieldsVisible = new SimpleBooleanProperty(false);

    private final TextField loginInput = new TextField();
    private final TextField passwordInput = new TextField();
    private final TextField displayNameInput = new TextField();

    private boolean checkInput() {
        boolean inputIsCorrect =
                (loginInput.getText() != null) && !loginInput.getText().isEmpty() &&
                (passwordInput.getText() != null) && !passwordInput.getText().isEmpty() &&
                (displayNameInput.getText() != null) && !displayNameInput.getText().isEmpty();

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
        if (!isLoggedIn.getValue() || !isAdmin.getValue())
            return;

        if (checkInput()) {
            try {
                String newLogin = loginInput.getText();
                String newPassword = passwordInput.getText();
                String newDisplayName = displayNameInput.getText();

                if (!newLogin.equals(account.getLogin()))
                    adapter.updateAccountLogin(account, newLogin);

                if (!newPassword.equals(account.getPassword()))
                    adapter.updateAccountPassword(account, newPassword);

                if (!newDisplayName.equals(account.getDisplayName()))
                    adapter.updateAccountDisplayName(account, newDisplayName);

            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }

    private void displayInfo() {
        if (!isLoggedIn.getValue() || !isAdmin.getValue())
            return;

        loginInput.setText(account.getLogin());
        passwordInput.setText(account.getPassword());
        displayNameInput.setText(account.getDisplayName());
    }

    public AdminAccountScreenBuilder(Stage stage, Account account, BooleanProperty isLoggedIn, BooleanProperty isAdmin, DBAdapter adapter) {
        this.stage = stage;
        this.account = account;
        this.isLoggedIn = isLoggedIn;
        this.isAdmin = isAdmin;
        this.adapter = adapter;
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

        isAdmin.addListener((ob, oldVal, newVal) -> displayInfo());

        return new ScrollPane(window);
    }
}
