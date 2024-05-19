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

        // Start a background thread for the task
        Thread backgroundThread = new Thread(() -> {
            app_tracker tracker = new app_tracker();
            screentime_tracker overall_tracker = new screentime_tracker();
            overall_tracker.startTracker();
            User32 user32 = User32.INSTANCE;

            // using if statements for now, but could potentially use
            // enums to map names to ints for use of switch statements instead
            while (HelloApplication.isProgramRunning()) {
                WinDef.HWND viewedWindow = user32.GetForegroundWindow();
                tracker.currentWindow = tracker.getCurrentWindow(viewedWindow, user32);
                tracker.startTracker();
                if (tracker.currentWindow.contains("Facebook")) {
                    tracker.stopTracker();
                    tracker.facebookTime += tracker.sessionTime;
                    tracker.startTracker();

                } else if (tracker.currentWindow.contains("Instagram")) {
                    tracker.stopTracker();
                    tracker.instagramTime += tracker.sessionTime;
                    tracker.startTracker();

                } else if (tracker.currentWindow.contains("YouTube")) {
                    tracker.stopTracker();
                    tracker.youtubeTime += tracker.sessionTime;
                    tracker.startTracker();


                }
            }
            tracker.stopTracker();
            overall_tracker.stopTracker();
            System.out.println("Facebook screen-time: " + tracker.facebookTime / 1000 + " seconds");
            System.out.println("Instagram screen-time: " + tracker.instagramTime / 1000 + " seconds");
            System.out.println("YouTube screen-time: " + tracker.youtubeTime / 1000 + " seconds");
            System.out.println("Overall screen-time: " + overall_tracker.getTime());
        });

        backgroundThread.setDaemon(true); // Set as daemon to stop when the application exits
        backgroundThread.start(); // Start the background thread
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
