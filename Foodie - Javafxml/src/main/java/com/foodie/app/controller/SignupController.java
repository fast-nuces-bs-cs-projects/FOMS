package com.foodie.app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;


public class SignupController {
    @FXML private BorderPane rootPane;
    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private Line eyeSlash;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField visibleConfirmPasswordField;
    @FXML private Line confirmEyeSlash;
    @FXML private Label feedbackLabel;

    private double dragOffsetX;
    private double dragOffsetY;

    @FXML
    private void initialize() {
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
        visibleConfirmPasswordField.textProperty().bindBidirectional(confirmPasswordField.textProperty());
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

    @FXML
    private void handleToggleConfirmPassword() {
        boolean showPassword = !visibleConfirmPasswordField.isVisible();
        visibleConfirmPasswordField.setVisible(showPassword);
        visibleConfirmPasswordField.setManaged(showPassword);
        confirmPasswordField.setVisible(!showPassword);
        confirmPasswordField.setManaged(!showPassword);
        confirmEyeSlash.setVisible(!showPassword);
        TextField activeField = showPassword ? visibleConfirmPasswordField : confirmPasswordField;
        activeField.requestFocus();
        activeField.positionCaret(confirmPasswordField.getText().length());
    }

    @FXML
    private void handleSignUp() {
        String name = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (name.isEmpty() || username.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showFeedback("Please fill out all fields.", true);
        } else if (!password.equals(confirmPassword)) {
            showFeedback("Passwords do not match.", true);
        } else {
            showFeedback("Account created successfully! You can now log in.", false);
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/app/view/login-view.fxml"));
            Stage stage = stage();
            stage.getScene().setRoot(loader.load());
        } catch (IOException exception) {
            showFeedback("Unable to open the login page.", true);
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

    private void showFeedback(String message, boolean error) {
        feedbackLabel.setText(message);
        feedbackLabel.getStyleClass().removeAll("feedback-error", "feedback-success");
        feedbackLabel.getStyleClass().add(error ? "feedback-error" : "feedback-success");
    }
}
