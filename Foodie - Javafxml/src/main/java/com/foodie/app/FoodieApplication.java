package com.foodie.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

import java.io.IOException;

public class FoodieApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                FoodieApplication.class.getResource("/com/foodie/app/view/login-view.fxml")
        );
        Scene scene = new Scene(loader.load(), 1000, 720);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Foodie");
        try {
            stage.getIcons().add(new Image(FoodieApplication.class.getResourceAsStream("/com/foodie/app/image/logo.png")));
        } catch (Exception e) {
            System.err.println("Could not load app icon: " + e.getMessage());
        }
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
