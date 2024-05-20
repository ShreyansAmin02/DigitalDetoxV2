package com.example.digitaldetox;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.digitaldetox.tracker.app_tracker;
import com.example.digitaldetox.tracker.screentime_tracker;

import java.io.IOException;

public class HelloApplication extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "Unplugify";
    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartPageView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();

    }

    public static boolean isProgramRunning() {
        if (primaryStage != null) {
            return primaryStage.isShowing();
        }
        return false;
    }

    public static void main(String[] args) {
        launch();

    }
}
