package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Builder;

import java.sql.SQLException;

public class LoginScreenBuilder implements Builder<Region> {
    private final Stage stage;
    private final Account account;
    private final BooleanProperty isLoggedIn;
    private final DBAdapter adapter;
    private final Runnable exitAuthenticationScreen;
    private final Runnable switchFromLoginScreen;

    private final BooleanProperty incorrectLoginVisible = new SimpleBooleanProperty(false);

    public LoginScreenBuilder(Stage stage, Account account, BooleanProperty isLoggedIn, DBAdapter adapter, Runnable exitAuthenticationScreen, Runnable switchFromLoginScreen) {
        this.stage = stage;
        this.account = account;
        this.isLoggedIn = isLoggedIn;
        this.adapter = adapter;
        this.exitAuthenticationScreen = exitAuthenticationScreen;
        this.switchFromLoginScreen = switchFromLoginScreen;
    }

    private void login(TextField loginInput, PasswordField passwordInput) {
        try {
            Account loginAccount = adapter.selectAccount(loginInput.getText());

            if ((loginAccount != null) && passwordInput.getText().equals(loginAccount.getPassword())) {
                account.setAccount(loginAccount);
                isLoggedIn.set(true);

                loginInput.clear();
                passwordInput.clear();
                incorrectLoginVisible.set(false);

                exitAuthenticationScreen.run();
            }
            else
                incorrectLoginVisible.set(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Region build() {
        GridPane window = new GridPane();
        window.prefWidthProperty().bind(stage.widthProperty());
        window.prefHeightProperty().bind(stage.heightProperty());
        window.setAlignment(Pos.CENTER);
        window.setHgap(10);  // gaps between columns
        window.setVgap(10);  // gaps between rows
//        window.setPadding(new Insets(25, 25, 25, 25));  // padding from the edges

        Text welcomeText = new Text("Log in your account");
        welcomeText.setFont(Font.font("Verdana", 50));
        window.add(welcomeText, 0, 0, 2, 1);

        Label loginLabel = new Label("Login:");
        window.add(loginLabel, 0, 1);

        TextField loginInput = new TextField();
        window.add(loginInput, 1, 1);

        Label passwordLabel = new Label("Password:");
        window.add(passwordLabel, 0, 2);

        PasswordField passwordInput = new PasswordField();
        window.add(passwordInput, 1, 2);

        Button logInButton = new Button("Log In");
        logInButton.setOnAction(e -> login(loginInput, passwordInput));

        HBox logInBox = new HBox(10);
        logInBox.setAlignment(Pos.BOTTOM_LEFT);
        logInBox.getChildren().add(logInButton);
        window.add(logInBox, 0, 3);

        Button createAccountButton = new Button("Create an account");
        createAccountButton.setOnAction(e -> switchFromLoginScreen.run());

        HBox createAccountBox = new HBox(10);
        createAccountBox.setAlignment(Pos.BOTTOM_RIGHT);
        createAccountBox.getChildren().add(createAccountButton);
        window.add(createAccountBox, 1, 3);

        Text incorrectLoginText = new Text("Incorrect login or password!");
        incorrectLoginText.setFont(Font.font("Verdana", 20));
        incorrectLoginText.setFill(Color.RED);
        incorrectLoginText.visibleProperty().bind(incorrectLoginVisible);
        window.add(incorrectLoginText, 0, 4, 2, 1);

        return window;
    }
}
