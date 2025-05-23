package com.main.cleaningservice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CleaningServiceApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new GlobalScreenBuilder(primaryStage).build());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
