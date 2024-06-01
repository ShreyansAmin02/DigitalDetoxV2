module com.example.digitaldetox {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.junit.jupiter;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires jbcrypt;


    opens com.example.digitaldetox to javafx.fxml;
    exports com.example.digitaldetox;
    exports com.example.digitaldetox.controller;
    opens com.example.digitaldetox.controller to javafx.fxml;
    exports com.example.digitaldetox.model;
    opens com.example.digitaldetox.model to javafx.fxml;
    exports com.example.digitaldetox.model.Goals;
    opens com.example.digitaldetox.model.Goals to javafx.fxml;
    exports com.example.digitaldetox.model.Screen_Tracker;
    opens com.example.digitaldetox.model.Screen_Tracker to javafx.fxml;
}