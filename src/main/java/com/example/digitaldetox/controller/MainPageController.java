package com.example.digitaldetox.controller;

import com.example.digitaldetox.GoalSettingClasses.AppFrame;
import com.example.digitaldetox.HelloApplication;
import com.example.digitaldetox.model.*;
import com.example.digitaldetox.tracker.ScreenTimeDAO;
import com.example.digitaldetox.tracker.app_tracker;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import com.example.digitaldetox.FeatureTimer.StartUpFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainPageController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button statsBtn;

    public MainPageController() {

    }


    @FXML
    protected void onLogoutButtonClick() throws IOException {
        UserSession.getInstance().clearSession();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartPageView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


//    @FXML
//    private void handleGoalsButtonClick(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/digitaldetox/ToDoListView.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setTitle("To-Do List");
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    private void handleTimerButtonClick() {
        StartUpFrame startUpFrame = new StartUpFrame();
        Stage stage = new Stage();
        stage.setScene(new Scene(startUpFrame, 400, 400)); // You can adjust size as needed
        stage.setTitle("Timer Setup");
        stage.show();
    }

    @FXML
    public void handleGoalsButtonClick(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/digitaldetox/GoalListView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("To-Do List");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onScreentimeButtonClick() throws IOException {
        Stage stage = (Stage) statsBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ScreentimeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setScene(scene);
    }




}

