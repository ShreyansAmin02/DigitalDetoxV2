package com.example.digitaldetox.controller;

import com.example.digitaldetox.HelloApplication;
import com.example.digitaldetox.model.IUserDAO;
import com.example.digitaldetox.model.User;
import com.example.digitaldetox.model.UserAccountDAO;
import com.example.digitaldetox.model.UserAuthentication;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPageController {
    @FXML
    public ImageView homeImage;
    @FXML
    private Button logoutButton;
    @FXML
    private Button loginToAccountButton;
    @FXML
    private Button signUpToAccountButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField confirmPasswordTextField;
    @FXML
    private Label signupstatus;
    @FXML
    private Label loginstatus;
    private IUserDAO userDAO;
    private UserAuthentication userAuthentication;

    public MainPageController() {
        userDAO = new UserAccountDAO();
        userAuthentication = new UserAuthentication(userDAO) ;
    }

    private void loginStatus() {

    }

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartPageView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartPageView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);

    }
    @FXML
    protected void onLoginToAccountClick() throws IOException {
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            loginstatus.setStyle("-fx-text-fill: #ff0000");
            loginstatus.setText("Please do not leave any fields blank.");
        }
        else if (userAuthentication.doesPasswordMatchUsername(usernameTextField.getText(), passwordTextField.getText())) {
            loginstatus.setStyle("-fx-text-fill: #006633;");
            loginstatus.setText("Login successful!");

            Stage stage = (Stage) loginToAccountButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPageView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);


            PauseTransition shortPause = new PauseTransition(Duration.seconds(3));
            shortPause.play();
            shortPause.setOnFinished(event -> stage.setScene(scene));


        } else {
            loginstatus.setStyle("-fx-text-fill: #ff0000");
            loginstatus.setText("Incorrect username or password.");
        }


    }

    @FXML
    protected void onSignUpToAccountClick() throws IOException {
        User user = new User(usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText());
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || emailTextField.getText().isEmpty()) {
            signupstatus.setText("Please do not leave any fields blank.");
        }
        else if (userAuthentication.isPasswordValid(user.getPassword()) && userAuthentication.isEmailValid(user.getEmail())
                && !userAuthentication.isUsernameTaken(user.getUsername())
                && userAuthentication.doesPasswordMatchConfirmPassword(user.getPassword(), confirmPasswordTextField.getText()))
        {
            Stage stage = (Stage) signUpToAccountButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPageView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            signupstatus.setStyle("-fx-text-fill: #006633;");
            signupstatus.setText("Sign up successful!");
            userDAO.addUser(user);

            PauseTransition shortPause = new PauseTransition(Duration.seconds(3));
            shortPause.play();
            shortPause.setOnFinished(event -> stage.setScene(scene));

        }

        else if (userAuthentication.isUsernameTaken(user.getUsername())){
            signupstatus.setStyle("-fx-text-fill: #ff0000");
            signupstatus.setText("Username has already been taken.");
        }

        else if (!userAuthentication.isEmailValid(user.getEmail())) {
            signupstatus.setStyle("-fx-text-fill: #ff0000");
            signupstatus.setText("Please enter a valid email.");
        }

        else if (!userAuthentication.isPasswordValid(user.getPassword())) {
            signupstatus.setStyle("-fx-text-fill: #ff0000");
            signupstatus.setText("Password does not meet minimum requirements.");
        } else if (!userAuthentication.doesPasswordMatchConfirmPassword(user.getPassword(), confirmPasswordTextField.getText())) {
            signupstatus.setStyle("-fx-text-fill: #ff0000");
            signupstatus.setText("Passwords do not match.");
        }

    }


}
