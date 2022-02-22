package pkg.foms.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pkg.foms.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Index {

    @FXML
    private Pane Signin_Pane;

    @FXML
    private TextField signin_username;

    @FXML
    private PasswordField signin_pswd;

    @FXML
    private Button submit;

    //-> Change UI from Index to Home
    @FXML
    void login(ActionEvent event) throws IOException {
        //-> Close Index Window
        Stage stage = (Stage) submit.getScene().getWindow();
        stage.close();
        //->Load Home Window
        AnchorPane dashboard =  FXMLLoader.load(HelloApplication.class.getResource("Home.fxml"));
        Stage newStage = new Stage();
        Scene newScene = new Scene(dashboard,1050,576);
        newStage.setTitle("Food Order Management System");
        newStage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("images/favicon.png"))));
        newStage.setResizable(false);
        newStage.setScene(newScene);
        newStage.show();
    }

}
