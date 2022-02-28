package pkg.foms.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONException;
import org.json.JSONObject;
import pkg.foms.Api.Item;
import pkg.foms.Api.User;
import pkg.foms.HelloApplication;
import pkg.foms.Modal.Mod_Login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Home {

    //--> FXML Variables

    //-> Pane
    @FXML
    public StackPane stackpane;
    @FXML
    public  Pane paneOverview;
    @FXML
    public  Pane paneAllOrders;
    @FXML
    public  Pane paneCustomerFeedback;
    @FXML
    public  Pane paneAddItem;
    @FXML
    public  Pane paneAddUser;

    //-> Pane : Add User
    @FXML
    public TextField txt_name;
    @FXML
    public TextField txt_email;
    @FXML
    public PasswordField txt_pswd;
    @FXML
    public TextField txt_ImgPathUser;
    @FXML
    public Button btn_uploadUserImg;

    //-> Pane : Add Item
    @FXML
    public TextField txt_ItemName;
    @FXML
    public TextArea txt_ItemDetail;
    @FXML
    public TextField txt_ImgPathItem;
    @FXML
    public Button btn_UploadItemImg;

    //-> Label
    @FXML
    private ImageView profImg;
    @FXML
    private Label username;
    @FXML
    private Label email_label;
    @FXML
    private Label labelTotalDelivered;
    @FXML
    private Label labelTotalOrders;
    @FXML
    private Label labelTotalPendingOrders;
    @FXML
    private Label labelTotalRejectedOrders;

    //-> Buttons
    @FXML
    private Button btn_Logout;

    //-> Message box
    Alert msg_box = new Alert(Alert.AlertType.NONE);

    //-> Class Call
    Mod_Login modalLogin = new Mod_Login();
    User ApiUser = new User();
    Item ApiItem = new Item();

    //-> Functions

    //-> Message Box
    void displayMessageBox(String msg,String Type){
        msg_box.setAlertType(Alert.AlertType.valueOf(Type));
        msg_box.setContentText(msg);
        msg_box.showAndWait();
    }

    //-> Logout
    @FXML
    void logout(ActionEvent event) throws IOException {
        //-> Close Home Window
        Stage stage = (Stage) btn_Logout.getScene().getWindow();
        stage.close();
        //-> Load Login Window
        AnchorPane Login_Page =  FXMLLoader.load(HelloApplication.class.getResource("Index.fxml"));
        Stage newStage = new Stage();
        Scene newScene = new Scene(Login_Page,388, 483);
        newStage.setTitle("Food Order Management System");
        newStage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("images/favicon.png"))));
        newStage.setResizable(false);
        newStage.setScene(newScene);
        newStage.show();
    }


    //--------------------------- Initialize ---------------------------
    public void initialize() throws JSONException, FileNotFoundException, MalformedURLException {
        //-> Disable all panes Except OverView
        paneAllOrders.setVisible(false);paneCustomerFeedback.setVisible(false);paneAddItem.setVisible(false);paneAddUser.setVisible(false);
        paneOverview.setVisible(true);

        //-> Load Info
        username.setText(modalLogin.getUserName());
        email_label.setText(modalLogin.getEmail());
        URL url = new URL(modalLogin.getImage());
        System.out.println(url);
        Image image = new Image(String.valueOf(url));
        profImg.setImage(image);

    }










}
