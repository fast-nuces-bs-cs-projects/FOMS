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
import pkg.foms.HelloApplication;

import java.io.File;
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

    //-> Message box
    Alert msg_box = new Alert(Alert.AlertType.NONE);


    //--> Functions

    //-> Switching Pane
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

    //-> Message Box
    void displayMessageBox(String msg,String Type){
        msg_box.setAlertType(Alert.AlertType.valueOf(Type));
        msg_box.setContentText(msg);
        msg_box.initStyle(StageStyle.UTILITY);
        msg_box.show();
    }


    //--------------------------- Add Item ---------------------------
    @FXML
    void UploadItemImg(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png*"));
        File file = fc.showOpenDialog(null);
        //->Set Image Path
        txt_ImgPathItem.setText(String.valueOf(file.getAbsoluteFile()));
    }


    @FXML
    void add_Item(ActionEvent event){
        String ItemName      = txt_ItemName.getText();
        String ItemDetail    = txt_ItemDetail.getText();
        String ItemImagePath = txt_ImgPathItem.getText();

        if(ItemName.isEmpty() || ItemDetail.isEmpty() || ItemImagePath.isEmpty()) {
            displayMessageBox("Please Complete all fields ..!!","WARNING");
        }
        else{
            displayMessageBox("Item added successfully ..!!","WARNING");
        }


    }




    //--------------------------- Initialize ---------------------------
    public void initialize() {
        //-> Disable all panes Except OverView
        paneAllOrders.setVisible(false);paneCustomerFeedback.setVisible(false);paneAddItem.setVisible(false);paneAddUser.setVisible(false);
        paneOverview.setVisible(true);




    }



    @FXML
    void add_User(ActionEvent event) {

    }






}
