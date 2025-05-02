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

public class GlobalLayoutBuilder implements Builder<Region> {
    Account currentAccount;
    DBAdapter adapter = new DBAdapter();

    @Override
    public Region build() {
        BorderPane window = new BorderPane();

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

            window.setCenter(errorBox);
            return window;
        }

        // Swapping between login screen and main GUI

        BooleanProperty loginScreenActive = new SimpleBooleanProperty(true);

        Region authenticationScreen = new AuthenticationScreenBuilder(currentAccount, adapter, () -> loginScreenActive.set(false)).build();
        Region mainGUI = new MainGUIBuilder(currentAccount, adapter, () -> loginScreenActive.set(true)).build();

        authenticationScreen.visibleProperty().bind(loginScreenActive);
        mainGUI.visibleProperty().bind(loginScreenActive.not());

        window.setCenter(new StackPane(authenticationScreen, mainGUI));
        return window;
    }

}
