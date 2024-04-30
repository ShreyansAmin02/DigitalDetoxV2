module com.example.digitaldetox {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.digitaldetox to javafx.fxml;
    exports com.example.digitaldetox;
    exports com.example.digitaldetox.controller;
    opens com.example.digitaldetox.controller to javafx.fxml;
}