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
import org.json.JSONException;

import pkg.foms.Api.Api_Item;
import pkg.foms.HelloApplication;
import pkg.foms.Modal.Mod_Item;
import pkg.foms.Modal.Mod_Login;
import pkg.foms.Modal.Mod_User;


import java.io.*;
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

    //-> Pane : Add Item
    @FXML
    public TextField txt_ItemName;
    @FXML
    public TextField txt_ItemPrice;
    @FXML
    public TextArea txt_ItemDetail;
    @FXML
    public TextField txt_ImgPathItem;

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

    //--> Call Class
    Mod_Login mod_login = new Mod_Login();
    Mod_User mod_user   = new Mod_User();
    Mod_Item mod_item   = new Mod_Item();

    //-> Message box
    Alert msg_box = new Alert(Alert.AlertType.NONE);
    void displayMessageBox(String msg,String Type){
        msg_box.setAlertType(Alert.AlertType.valueOf(Type));
        msg_box.setContentText(msg);
        msg_box.showAndWait();
    }

    //-> Functions

    //-> Switching Pane

    @FXML
    void overview(ActionEvent event) {paneOverview.setVisible(true);stackpane.getChildren().setAll(paneOverview);}

    @FXML
    void allOrders(ActionEvent event) {paneAllOrders.setVisible(true);stackpane.getChildren().setAll(paneAllOrders);}

    @FXML
    void feedback(ActionEvent event) {paneCustomerFeedback.setVisible(true);stackpane.getChildren().setAll(paneCustomerFeedback);}

    @FXML
    void addItem(ActionEvent event) {paneAddItem.setVisible(true);stackpane.getChildren().setAll(paneAddItem);}

    @FXML
    void addUser(ActionEvent event) {paneAddUser.setVisible(true);stackpane.getChildren().setAll(paneAddUser);}

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
    void add_Item(ActionEvent event) throws JSONException, IOException, URISyntaxException {
        mod_item.setItemName(txt_ItemName.getText());
        mod_item.setItemPrice(txt_ItemPrice.getText());
        mod_item.setItemDetail(txt_ItemDetail.getText());
        mod_item.setItemImagePath(Path.of(txt_ImgPathItem.getText()));

        if(txt_ItemName.getText().isEmpty() && txt_ItemPrice.getText().isEmpty() &&
                txt_ItemDetail.getText().isEmpty() && txt_ImgPathItem.getText().isEmpty()){
            displayMessageBox("Please Complete all fields & Check if Image Exist on Path ..!!","WARNING");
        }
        else{
            String msg = mod_item.add_Item();
            if(msg.equals("true")){
                displayMessageBox("Added Successfully ..!!","INFORMATION");
                txt_name.setText("");txt_email.setText("");txt_pswd.setText("");txt_ImgPathUser.setText("");
            }
            else{displayMessageBox("Error ..!! Item Already Exists","WARNING");}
        }
    }

    //--------------------------- Add User ---------------------------
    @FXML
    void uploadUserImg(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png*"));
        File file = fc.showOpenDialog(null);
        //->Set Image Path
        txt_ImgPathUser.setText(String.valueOf(file.getAbsoluteFile()));
    }

    @FXML
    void add_User(ActionEvent event) throws IOException, URISyntaxException, JSONException {
        mod_user.setName(txt_name.getText());
        mod_user.setEmail(txt_email.getText());
        mod_user.setPswd(txt_pswd.getText());
        mod_user.setUserImagePath(Path.of(txt_ImgPathUser.getText()));

        if(txt_name.getText().isEmpty() && txt_email.getText().isEmpty() &&
                txt_pswd.getText().isEmpty() && txt_ImgPathItem.getText().isEmpty()){
            displayMessageBox("Please Complete all fields & Check if Image Exist on Path ..!!","WARNING");
        }
        else{
            String msg = mod_user.add_user();
            if(msg.equals("true")){
                displayMessageBox("Registered Successfully ..!!","INFORMATION");
                txt_name.setText("");txt_email.setText("");txt_pswd.setText("");txt_ImgPathUser.setText("");
            }
            else{displayMessageBox("Error ..!! User Already Exists","WARNING");}
        }
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
        username.setText(mod_login.getUserName());
        email_label.setText(mod_login.getEmail());
        URL url = new URL(mod_login.getImage());
        System.out.println(url);
        Image image = new Image(String.valueOf(url));
        profImg.setImage(image);

    }










}
