package com.example.digitaldetox.controller;

import com.example.digitaldetox.HelloApplication;
import com.example.digitaldetox.model.UserSession;
import com.example.digitaldetox.model.Screen_Tracker.ScreenTimeDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScreentimeController implements Initializable {
    @FXML
    private Button back;
    @FXML
    AnchorPane chartPane;
    private BarChart<String, Number> barChart;
    private ScreenTimeDAO screenTimeDAO;
    private int userID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userID = UserSession.getInstance().getLoggedInUser().getaccountId();
    }

    public void setScreenTimeDAO(ScreenTimeDAO screenTimeDAO) {
        this.screenTimeDAO = screenTimeDAO;
        createBarChart();
        updateBarChart();
    }

    public void onRefreshButtonClick() {
        screenTimeDAO.addAppScreentime(userID);
        screenTimeDAO.addOverallScreentime(userID);
        screenTimeDAO.getAllAppsScreentime(userID);
        screenTimeDAO.getOverallScreentime(userID);
        screenTimeDAO.resetTrackers();
        updateBarChart();
    }
    public void onBackButtonClick() throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/digitaldetox/view/MainPageView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    private void createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Screen-time Tracker");
        xAxis.setLabel("App Name");
        yAxis.setLabel("Time Spent (Seconds)");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        barChart.getData().add(series);

        chartPane.getChildren().add(barChart);
    }

    private void updateBarChart() {
        String[] appNames = screenTimeDAO.getAppNames(userID);
        long[] appDurations = screenTimeDAO.getAppDurations(userID);

        String[] appName = new String[6];
        long[] appDuration = new long[6];

        System.arraycopy(appNames, 0, appName, 0, appNames.length);
        System.arraycopy(appDurations, 0, appDuration, 0, appDurations.length);

        appName[5] = "Overall Screentime";
        appDuration[5] = screenTimeDAO.getOverallScreentime(userID);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < appName.length; i++) {
            series.getData().add(new XYChart.Data<>(appName[i], appDuration[i]));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
    }

}