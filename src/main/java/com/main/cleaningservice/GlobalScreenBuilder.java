package com.main.cleaningservice;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class GlobalScreenBuilder implements Builder<Region> {
    @Override
    public Region build() {
        DBAdapter adapter = new DBAdapter();
        StackPane window = new StackPane();

        // Error handling

        try {
            adapter.connect();
        } catch (RuntimeException e) {
            VBox errorBox = new VBox();

            Text bigErrorText = new Text("ERROR!");
            bigErrorText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
            bigErrorText.setFill(Color.RED);
            errorBox.getChildren().add(bigErrorText);

            TextField smallErrorText = new TextField(e.toString());
            smallErrorText.setFont(Font.font("Verdana", 30));
            smallErrorText.setEditable(false);
            errorBox.getChildren().add(smallErrorText);

            window.getChildren().add(errorBox);
            return window;
        }

        // Swapping between login screen and main screen

        BooleanProperty loginScreenActive = new SimpleBooleanProperty(true);
        BooleanProperty isLoggedIn = new SimpleBooleanProperty(false);

        Account account = new Account(-1, null, null, null, null);

        Region authenticationScreen = new AuthenticationScreenBuilder(account, isLoggedIn, adapter, () -> loginScreenActive.set(false)).build();
        Region mainGUI = new MainScreenBuilder(account, isLoggedIn, adapter, () -> loginScreenActive.set(true)).build();

        authenticationScreen.visibleProperty().bind(loginScreenActive);
        mainGUI.visibleProperty().bind(loginScreenActive.not());

        window.getChildren().addAll(authenticationScreen, mainGUI);
        return window;
    }

}
