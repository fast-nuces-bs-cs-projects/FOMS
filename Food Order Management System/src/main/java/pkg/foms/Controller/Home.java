package pkg.foms.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Home {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnAlddItem;

    @FXML
    private Button btnAllOrders;

    @FXML
    private Button btnCompletedOrders;

    @FXML
    private Button btnCustomerFeedBack;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnPendingOrders;

    @FXML
    private Button btnRejectedOrders;

    @FXML
    private Button btnSignout;

    @FXML
    private ImageView imgUserLogin;

    @FXML
    private Label labelLoginUserName;

    @FXML
    private Label labelTotalDelivered;

    @FXML
    private Label labelTotalOrders;

    @FXML
    private Label labelTotalPendingOrders;

    @FXML
    private Label labelTotalRejectedOrders;

    @FXML
    private Pane paneOverview;

    @FXML
    private StackPane stackPane;

    @FXML
    void handleAddItem(ActionEvent event) {

    }

    @FXML
    void handleAddUser(ActionEvent event) {

    }

    @FXML
    void handleAllOrders(ActionEvent event) {

        StackPane stakp  = (StackPane) stackPane.getChildren();

        System.out.println(stakp);
    }

    @FXML
    void handleCompletedOrders(ActionEvent event) {

    }

    @FXML
    void handleCustomerFeedback(ActionEvent event) {

    }

    @FXML
    void handleOverView(ActionEvent event) {

    }

    @FXML
    void handlePendingOrders(ActionEvent event) {

    }

    @FXML
    void handleRejectedOrders(ActionEvent event) {

    }

    @FXML
    void handleSignout(ActionEvent event) {

    }

}
