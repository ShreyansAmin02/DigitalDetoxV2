package com.example.digitaldetox.model.Timer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimerApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StartUpFrame startUpFrame = new StartUpFrame();
        Scene scene = new Scene(startUpFrame, 300, 200); // Adjust size as needed
        primaryStage.setTitle("Timer Setup");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}