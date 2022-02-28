package pkg.foms.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Home_SwitchPanes extends  Home{

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
}
