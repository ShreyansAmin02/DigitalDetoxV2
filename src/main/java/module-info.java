module com.example.digitaldetox {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.digitaldetox to javafx.fxml;
    exports com.example.digitaldetox;
}