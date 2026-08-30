package com.foodie.app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class MainController {
    @FXML private BorderPane mainRoot;
    @FXML private StackPane contentArea;
    @FXML private Button btnDashboard;
    @FXML private Button btnOrders;
    @FXML private Button btnMenu;
    @FXML private Button btnCustomers;

    private double dragOffsetX;
    private double dragOffsetY;
    
    private final Map<String, Parent> viewCache = new HashMap<>();

    @FXML
    private void initialize() {
        // Run later to ensure stage is available, but for switching views we don't strictly need the stage.
        javafx.application.Platform.runLater(this::handleDashboard);
    }

    @FXML private void handleDashboard() { loadView("dashboard-view.fxml", btnDashboard); }
    @FXML private void handleOrders() { loadView("orders-view.fxml", btnOrders); }
    @FXML private void handleMenu() { loadView("menu-view.fxml", btnMenu); }
    @FXML private void handleCustomers() { loadView("customers-view.fxml", btnCustomers); }

    private void loadView(String fxml, Button activeButton) {
        try {
            Parent view = viewCache.get(fxml);
            if (view == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/app/view/" + fxml));
                view = loader.load();
                viewCache.put(fxml, view);
            }
            contentArea.getChildren().setAll(view);
            
            Button[] buttons = {btnDashboard, btnOrders, btnMenu, btnCustomers};
            for (Button btn : buttons) {
                btn.getStyleClass().remove("nav-selected");
            }
            if (activeButton != null) {
                activeButton.getStyleClass().add("nav-selected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/foodie/app/view/login-view.fxml"));
            Stage stage = stage();
            stage.getScene().setRoot(loader.load());
            stage.setWidth(1000);
            stage.setHeight(720);
            stage.setMinWidth(900);
            stage.setMinHeight(650);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleWindowPressed(MouseEvent event) { dragOffsetX = event.getScreenX() - stage().getX(); dragOffsetY = event.getScreenY() - stage().getY(); }
    @FXML private void handleWindowDragged(MouseEvent event) { if (!stage().isMaximized()) { stage().setX(event.getScreenX() - dragOffsetX); stage().setY(event.getScreenY() - dragOffsetY); } }
    @FXML private void handleMinimize() { stage().setIconified(true); }
    @FXML private void handleMaximize() { stage().setMaximized(!stage().isMaximized()); }
    @FXML private void handleClose() { javafx.application.Platform.exit(); System.exit(0); }
    private Stage stage() { return (Stage) mainRoot.getScene().getWindow(); }
}

