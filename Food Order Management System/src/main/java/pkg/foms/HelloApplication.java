package pkg.foms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Index.fxml"));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/favicon.png"))));
        Scene scene = new Scene(fxmlLoader.load(), 388, 483);
        stage.setTitle("Food Order Management System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}