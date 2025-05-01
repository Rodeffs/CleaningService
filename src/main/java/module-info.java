module com.main.cleaningservice {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.main.cleaningservice to javafx.fxml;
    exports com.main.cleaningservice;
}