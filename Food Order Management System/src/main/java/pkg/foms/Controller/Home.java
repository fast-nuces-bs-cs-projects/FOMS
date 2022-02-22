package pkg.foms.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pkg.foms.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class Home {

    //--> FXML Variables

    //-> Pane
    @FXML
    private StackPane stackpane;
    @FXML
    private Pane paneOverview;
    @FXML
    private Pane paneAllOrders;
    @FXML
    private Pane paneCustomerFeedback;
    @FXML
    private Pane paneAddItem;
    @FXML
    private Pane paneAddUser;

    //-> Pane : Add User
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_email;
    @FXML
    private PasswordField txt_pswd;
    @FXML
    private TextField txt_ImgPathUser;
    @FXML
    private Button btn_uploadUserImg;

    //-> Pane : Add Item
    @FXML
    private TextField txt_ItemName;
    @FXML
    private TextArea txt_ItemDetail;
    @FXML
    private TextField txt_ImgPathItem;
    @FXML
    private Button btn_UploadItemImg;

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


    //--> Functions

    //--> Switching Pane
    @FXML
    void overview(ActionEvent event) {
        paneOverview.setVisible(true);
        stackpane.getChildren().setAll(paneOverview);
    }

    @FXML
    void allOrders(ActionEvent event) {
        paneAllOrders.setVisible(true);
        stackpane.getChildren().setAll(paneAllOrders);
    }

    @FXML
    void feedback(ActionEvent event) {
        paneCustomerFeedback.setVisible(true);
        stackpane.getChildren().setAll(paneCustomerFeedback);
    }

    @FXML
    void addItem(ActionEvent event) {
        paneAddItem.setVisible(true);
        stackpane.getChildren().setAll(paneAddItem);
    }

    @FXML
    void addUser(ActionEvent event) {
        paneAddUser.setVisible(true);
        stackpane.getChildren().setAll(paneAddUser);
    }

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



    //------------------------------------------
    @FXML
    void add_Item(ActionEvent event){

    }
    @FXML
    void add_User(ActionEvent event) {

    }



    //--------------------------- Initialize ---------------------------
    public void initialize() {
        //-> Disable all panes Except OverView
        paneAllOrders.setVisible(false);paneCustomerFeedback.setVisible(false);paneAddItem.setVisible(false);paneAddUser.setVisible(false);
        paneOverview.setVisible(true);

        


    }






}
