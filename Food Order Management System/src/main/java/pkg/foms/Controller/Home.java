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

    @FXML
    private Button btn_addItem;

    @FXML
    private Button btn_addUser;

    @FXML
    private Button btn_uploadImg;

    @FXML
    private Button btn_uploadUserImg;

    @FXML
    private Button create_folder;

    @FXML
    private Label email_label;

    @FXML
    private AnchorPane home_window;

    @FXML
    private Label labelTotalDelivered;

    @FXML
    private Label labelTotalOrders;

    @FXML
    private Label labelTotalPendingOrders;

    @FXML
    private Label labelTotalRejectedOrders;

    @FXML
    private Pane paneAddItem;

    @FXML
    private Pane paneAddUser;

    @FXML
    private Pane paneAllOrders;

    @FXML
    private Pane paneCustomerFeedback;

    @FXML
    private Pane paneOverview;

    @FXML
    private Pane pnlMenus;

    @FXML
    private ImageView profImg;

    @FXML
    private StackPane stackpane;

    @FXML
    private TextField txt_ImgPath;

    @FXML
    private TextArea txt_ItemDetail;

    @FXML
    private TextField txt_ItemName;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_imgPath;

    @FXML
    private TextField txt_name;

    @FXML
    private PasswordField txt_pswd;

    @FXML
    private Label username;

    @FXML
    private VBox vbox;

    @FXML
    private Button btn_Logout;

    //--> Switching Pane
    @FXML
    void overview(ActionEvent event) {
        stackpane.getChildren().setAll(paneOverview);
    }

    @FXML
    void allOrders(ActionEvent event) {
        stackpane.getChildren().setAll(paneAllOrders);
    }

    @FXML
    void feedback(ActionEvent event) {
        stackpane.getChildren().setAll(paneCustomerFeedback);

    }

    @FXML
    void addItem(ActionEvent event) {
        stackpane.getChildren().setAll(paneAddItem);

    }

    @FXML
    void addUser(ActionEvent event) {
        stackpane.getChildren().setAll(paneAddUser);

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        //-> Close Home Window
        Stage stage = (Stage) btn_Logout.getScene().getWindow();
        stage.close();
        //-> Load Login Page
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
    void add_User(ActionEvent event) {

    }







}
