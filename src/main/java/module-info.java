module com.example.cleaningservice {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cleaningservice to javafx.fxml;
    exports com.example.cleaningservice;
}