package com.example.digitaldetox.FeatureTimer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer extends BorderPane {
    private Label countdownLabel;
    private Button overrideButton;
    private Timer timer;
    private int timeLeft; // time left in seconds
    private long lockoutDuration; // lockout duration in milliseconds
    private int iterations;

    private int ignoresCount = 0; //

    public CountdownTimer(int totalTime, long lockoutDuration, int iterations) {
        this.timeLeft = totalTime;
        this.lockoutDuration = lockoutDuration;
        this.iterations = iterations;
        initializeComponents();
        startCountdown();
    }

    private void initializeComponents() {
        countdownLabel = new Label(formatTime(timeLeft));
        countdownLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");

        overrideButton = new Button("Override Lockout");
        overrideButton.setStyle("-fx-background-color: white; -fx-border-color: #8B0000; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        overrideButton.setOnAction(e -> overrideLockout());

        VBox vbox = new VBox(10, countdownLabel, overrideButton);
        vbox.setStyle("-fx-alignment: center; -fx-padding: 20px;");
        setCenter(vbox);
    }

    private void startCountdown() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private int count = 0;

            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    Platform.runLater(() -> countdownLabel.setText("Time Left: " + formatTime(timeLeft)));
                } else {
                    count++;
                    if (count >= iterations) {
                        timer.cancel();
                        Platform.runLater(() -> showCompletionDialog());
                    } else {
                        timeLeft = (int) (lockoutDuration / 1000);
                        Platform.runLater(() -> countdownLabel.setText("Time Left: " + formatTime(timeLeft)));
                    }
                }
            }
        }, 1000, 1000);
    }

    private void stopCountdown() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            Platform.runLater(() -> countdownLabel.setText("Timer stopped"));
        }
    }

    private void showCompletionDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Timer Alert");
        alert.setHeaderText("Timer triggered");
        alert.setContentText("Do you want to continue?");

        ButtonType buttonIgnore = new ButtonType("Ignore");
        ButtonType buttonLockout = new ButtonType("Lockout");

        alert.getButtonTypes().setAll(buttonIgnore, buttonLockout);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonIgnore) {
            System.out.println("User chose to Ignore.");
            ignoresCount++;

        } else if (result.get() == buttonLockout) {
            System.out.println("User chose lockout.");
            startLockout();
        }
    }

    private void startLockout() {
        int Lockout = (int) (lockoutDuration / 1000);
        Platform.runLater(() -> {
            countdownLabel.setText("Locked out for: " + formatTime(Lockout));
            overrideButton.setVisible(true);
        });
        startCountdown();
    }

    private void overrideLockout() {
        stopCountdown();
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION, "Lockout overridden.");
            alert.showAndWait();
        });
        ((Stage) getScene().getWindow()).close(); // Close the window after overriding
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}