package com.example.digitaldetox.FeatureTimer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartUpFrame extends VBox {
    private TextField periodField, iterationsField, lockoutField;

    public StartUpFrame() {
        super(10); // VBox with spacing of 10
        setPadding(new Insets(15)); // Padding around the VBox
        initializeComponents();
    }

    private void initializeComponents() {
        periodField = new TextField();
        periodField.setPromptText("Enter period (seconds)");

        iterationsField = new TextField();
        iterationsField.setPromptText("Enter number of iterations");

        lockoutField = new TextField();
        lockoutField.setPromptText("Enter lockout time (seconds)");

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startMainApplication());

        this.getChildren().addAll(new Label("Timer Setup"), periodField, iterationsField, lockoutField, startButton);
    }

    private void startMainApplication() {
        try {
            long period = Long.parseLong(periodField.getText()) * 1000;
            int iterations = Integer.parseInt(iterationsField.getText());
            long lockout = Long.parseLong(lockoutField.getText());

            CountdownTimer countdownTimer = new CountdownTimer((int) (period / 1000), lockout, iterations);
            Stage stage = new Stage();
            stage.setScene(new Scene(countdownTimer, 300, 200));
            stage.setTitle("Countdown Timer");
            stage.show();
        } catch (NumberFormatException e) {
            // Handle number format exception
            System.out.println("Invalid input. Please enter valid numbers.");
        }
    }
}