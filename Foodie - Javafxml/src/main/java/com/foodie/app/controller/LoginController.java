package com.foodie.app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import com.foodie.app.service.ServiceLocator;
import com.foodie.app.service.AuthService;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;


public class LoginController {
    @FXML private BorderPane rootPane;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private Label feedbackLabel;
    @FXML private Line eyeSlash;

    private double dragOffsetX;
    private double dragOffsetY;

    @FXML
    private void initialize() {
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    @FXML
    private void handleTogglePassword() {
        boolean showPassword = !visiblePasswordField.isVisible();
        visiblePasswordField.setVisible(showPassword);
        visiblePasswordField.setManaged(showPassword);
        passwordField.setVisible(!showPassword);
        passwordField.setManaged(!showPassword);
        eyeSlash.setVisible(!showPassword);
        TextField activeField = showPassword ? visiblePasswordField : passwordField;
        activeField.requestFocus();
        activeField.positionCaret(passwordField.getText().length());
    }

    private final AuthService authService = ServiceLocator.getAuthService();

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        if (email.isEmpty() || password.isEmpty()) {
            showFeedback("Please enter your email and password.", true);
            return;
        } 
        
        authService.login(email, password).thenAccept(token -> {
            Platform.runLater(this::openDashboard);
        }).exceptionally(ex -> {
            Platform.runLater(() -> showFeedback("Login failed: Invalid credentials.", true));
            return null;
        });
    }

    @FXML
    private void handleSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/app/view/signup-view.fxml"));
            Stage stage = stage();
            stage.getScene().setRoot(loader.load());
        } catch (IOException exception) {
            showFeedback("Unable to open the signup page.", true);
            exception.printStackTrace();
        }
    }

    @FXML
    private void handleWindowPressed(MouseEvent event) {
        Stage stage = stage();
        dragOffsetX = event.getScreenX() - stage.getX();
        dragOffsetY = event.getScreenY() - stage.getY();
    }

    @FXML
    private void handleWindowDragged(MouseEvent event) {
        Stage stage = stage();
        if (!stage.isMaximized()) {
            stage.setX(event.getScreenX() - dragOffsetX);
            stage.setY(event.getScreenY() - dragOffsetY);
        }
    }

    @FXML private void handleMinimize() { stage().setIconified(true); }
    @FXML private void handleMaximize() { stage().setMaximized(!stage().isMaximized()); }
    @FXML private void handleClose() { javafx.application.Platform.exit(); System.exit(0); }

    private Stage stage() { return (Stage) rootPane.getScene().getWindow(); }

    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/app/view/main-layout.fxml"));
            Stage stage = stage();
            stage.getScene().setRoot(loader.load());
            stage.setMaximized(false);
            stage.setWidth(1300);
            stage.setHeight(840);
            stage.setMinWidth(1000);
            stage.setMinHeight(650);
            stage.centerOnScreen();
        } catch (IOException exception) {
            showFeedback("Unable to open the dashboard.", true);
            exception.printStackTrace();
        }
    }

    private void showFeedback(String message, boolean error) {
        feedbackLabel.setText(message);
        feedbackLabel.getStyleClass().removeAll("feedback-error", "feedback-success");
        feedbackLabel.getStyleClass().add(error ? "feedback-error" : "feedback-success");
    }
}
