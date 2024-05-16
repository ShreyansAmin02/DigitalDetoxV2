package com.example.digitaldetox.FeatureTimer;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Alert;

public class StartUpFrame extends VBox {
    private TextField periodField, iterationsField, lockoutField;
    private Timer timer;

    public StartUpFrame() {
        // Set up the layout
        this.setPadding(new Insets(15));
        this.setSpacing(10);

        // Initialize components
        createForm();
    }

    private void createForm() {
        periodField = new TextField();
        periodField.setPromptText("Enter period (seconds)");

        iterationsField = new TextField();
        iterationsField.setPromptText("Enter number of iterations");

        lockoutField = new TextField();
        lockoutField.setPromptText("Enter lockout time (seconds)");

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startMainApplication());

        // Add components to VBox
        this.getChildren().addAll(new Label("Timer Setup"), periodField, iterationsField, lockoutField, startButton);
    }

    private void startMainApplication() {
        int initialDelay = 1 * 1000; // Initial delay in milliseconds
        long period = Long.parseLong(periodField.getText()) * 1000; // Period in milliseconds
        int iterations = Integer.parseInt(iterationsField.getText()); // Number of iterations
        long lockout = Long.parseLong(lockoutField.getText()) * 1000; // Lockout in milliseconds

        timer = new Timer("Scheduled Task");
        TimerTask task = createTask(iterations, period, lockout);
        timer.schedule(task, initialDelay, period);
    }

    private TimerTask createTask(int iterations, long period, long lockout) {
        return new TimerTask() {
            private int count = 0;

            public void run() {
                if (++count >= iterations) {
                    timer.cancel();
                    javafx.application.Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Task completed and timer stopped.");
                        alert.showAndWait();
                    });
                }
            }
        };
    }
}
