package com.example.digitaldetox.controller;

import com.example.digitaldetox.HelloApplication;
import com.example.digitaldetox.model.*;
import com.example.digitaldetox.model.Screen_Tracker.ScreenTimeDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.digitaldetox.model.Timer.StartUpFrame;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Button statsBtn;
    ScreenTimeDAO screenTimeDAO;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        screenTimeDAO = new ScreenTimeDAO();
        screenTimeDAO.initialiseTrackers();
        screenTimeDAO.beginTracking();
    }

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        UserSession.getInstance().clearSession();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/digitaldetox/view/StartPageView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void handleTimerButtonClick() {
        StartUpFrame startUpFrame = new StartUpFrame();
        Stage stage = new Stage();
        stage.setScene(new Scene(startUpFrame, 400, 400));
        stage.setTitle("Timer Setup");
        stage.show();
    }

    @FXML
    public void handleGoalsButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/digitaldetox/view/GoalListView.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/digitaldetox/view/ScreentimeView.fxml"));
        Parent root = fxmlLoader.load();

        // Get the controller instance from the loader
        ScreentimeController screentimeController = fxmlLoader.getController();

        // Pass the screenTimeDAO instance to ScreentimeController
        screentimeController.setScreenTimeDAO(screenTimeDAO);

        Stage stage = (Stage) statsBtn.getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
    }




}