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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class MainPageController {
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
    private Label signupStatus;
    @FXML
    private Label loginStatus;
    private IUserDAO userDAO;
    private UserAuthentication userAuthentication;

    public MainPageController() {
        userDAO = new UserAccountDAO();
        userAuthentication = new UserAuthentication(userDAO);
    }

    private void loginStatus() {
        // Implementation for login status
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
            loginStatus.setStyle("-fx-text-fill: #ff0000");
            loginStatus.setText("Please do not leave any fields blank.");
        } else {
            User user = userDAO.getUserByUsername(usernameTextField.getText());
            if (user != null && BCrypt.checkpw(passwordTextField.getText(), user.getPassword())) {
                loginStatus.setStyle("-fx-text-fill: #006633;");
                loginStatus.setText("Login successful!");

                Stage stage = (Stage) loginToAccountButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPageView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);

                PauseTransition shortPause = new PauseTransition(Duration.seconds(3));
                shortPause.play();
                shortPause.setOnFinished(event -> stage.setScene(scene));
            } else {
                loginStatus.setStyle("-fx-text-fill: #ff0000");
                loginStatus.setText("Incorrect username or password.");
            }
        }
    }

    @FXML
    protected void onSignUpToAccountClick() throws IOException {
        String plainPassword = passwordTextField.getText();
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        User user = new User(usernameTextField.getText(), hashedPassword, emailTextField.getText());

        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || emailTextField.getText().isEmpty()) {
            signupStatus.setStyle("-fx-text-fill: #ff0000;");
            signupStatus.setText("Please do not leave any fields blank.");
        } else if (userAuthentication.isPasswordValid(plainPassword) && userAuthentication.isEmailValid(user.getEmail())
                && !userAuthentication.isUsernameTaken(user.getUsername())
                && userAuthentication.doesPasswordMatchConfirmPassword(plainPassword, confirmPasswordTextField.getText())) {
            Stage stage = (Stage) signUpToAccountButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPageView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            signupStatus.setStyle("-fx-text-fill: #006633;");
            signupStatus.setText("Sign up successful!");
            userDAO.addUser(user);

            PauseTransition shortPause = new PauseTransition(Duration.seconds(3));
            shortPause.play();
            shortPause.setOnFinished(event -> stage.setScene(scene));
        } else if (userAuthentication.isUsernameTaken(user.getUsername())) {
            signupStatus.setStyle("-fx-text-fill: #ff0000;");
            signupStatus.setText("Username has already been taken.");
        } else if (!userAuthentication.isEmailValid(user.getEmail())) {
            signupStatus.setStyle("-fx-text-fill: #ff0000;");
            signupStatus.setText("Please enter a valid email.");
        } else if (!userAuthentication.isPasswordValid(plainPassword)) {
            signupStatus.setStyle("-fx-text-fill: #ff0000;");
            signupStatus.setText("Password does not meet minimum requirements.");
        } else if (!userAuthentication.doesPasswordMatchConfirmPassword(plainPassword, confirmPasswordTextField.getText())) {
            signupStatus.setStyle("-fx-text-fill: #ff0000;");
            signupStatus.setText("Passwords do not match.");
        }
    }
}
