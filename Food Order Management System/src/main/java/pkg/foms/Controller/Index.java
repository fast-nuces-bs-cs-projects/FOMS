package pkg.foms.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import pkg.foms.HelloApplication;
import pkg.foms.Modal.Mod_Login;
import pkg.foms.Modal.Mod_User;

import java.io.IOException;
import java.util.Objects;

public class Index {

    //-> Variables
    @FXML
    private TextField signin_username;
    @FXML
    private PasswordField signin_pswd;
    @FXML
    private Button submit;



    //-> Message box
    Alert msg_box = new Alert(Alert.AlertType.NONE);

    //-> Call Class
    //Login login = new Login();
    Mod_Login modalLogin = new Mod_Login();
    Mod_User modeUser = new Mod_User();
    //--> Functions

    //-> Change UI from Index to Home
    private void load_Dashboard() throws IOException {
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

    @FXML
    void login(ActionEvent event) throws IOException, JSONException {
        modeUser.setEmail(signin_username.getText());
        modeUser.setPswd(signin_pswd.getText());
        String result = modeUser.verifyCredentials();

        try {
            JSONObject  userInfo = new JSONObject(result);
            modalLogin.setId(String.valueOf(userInfo.get("ID")));
            modalLogin.setUserName(String.valueOf(userInfo.get("Name")));
            modalLogin.setEmail(String.valueOf(userInfo.get("Email")));
            modalLogin.setImage(String.valueOf(userInfo.get("Img")));
            load_Dashboard();
        } catch (JSONException e) {
            msg_box.setAlertType(Alert.AlertType.ERROR);
            msg_box.setContentText("Invalid Credentials ..!!"+"\n");
            msg_box.showAndWait();
        }


    }

}
