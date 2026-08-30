package com.foodie.app.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import com.foodie.app.model.Customer;
import com.foodie.app.service.CustomerService;
import com.foodie.app.service.ServiceLocator;
import javafx.application.Platform;

public class CustomersController {
        @FXML private Label totalCustomersValue;
    @FXML private Label activeCustomersValue;
    @FXML private Label inactiveCustomersValue;
    @FXML private TableView<Customer> customersTable;
    @FXML private TableColumn<Customer, String> idColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, Number> ordersColumn;
    @FXML private TableColumn<Customer, String> spentColumn;
    @FXML private TableColumn<Customer, String> statusColumn;
    @FXML private TableColumn<Customer, Customer> actionsColumn;

    private ObservableList<Customer> allCustomers;
        
    

    private final CustomerService customerService = ServiceLocator.getCustomerService();

    @FXML
    private void initialize() {
        configureTable();
        allCustomers = FXCollections.observableArrayList();
        customersTable.setItems(allCustomers);
        loadCustomers();
    }

    private void loadCustomers() {
        customerService.getCustomers().thenAccept(customers -> {
            Platform.runLater(() -> {
                allCustomers.setAll(customers);
                updateSummaryCounters();
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }
    
    private void updateSummaryCounters() {
        if (totalCustomersValue != null) totalCustomersValue.setText(String.valueOf(allCustomers.size()));
        long active = allCustomers.stream().filter(c -> "Active".equalsIgnoreCase(c.status())).count();
        if (activeCustomersValue != null) activeCustomersValue.setText(String.valueOf(active));
        long inactive = allCustomers.stream().filter(c -> "Inactive".equalsIgnoreCase(c.status())).count();
        if (inactiveCustomersValue != null) inactiveCustomersValue.setText(String.valueOf(inactive));
    }

    @FXML
    private void handleFilter(javafx.event.ActionEvent event) {
        ToggleButton button = (ToggleButton) event.getSource();
        String filter = (String) button.getUserData();
        if ("ALL".equals(filter)) {
            customersTable.setItems(allCustomers);
        } else {
            customersTable.setItems(allCustomers.filtered(c -> filter.equalsIgnoreCase(c.status())));
        }
    }

    private void configureTable() {
        customersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        idColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().id()));
        nameColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().name()));
        phoneColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().phone()));
        ordersColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().totalOrders()));
        spentColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(String.format("%,.2f", cell.getValue().totalSpent())));
        
        statusColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().status()));
        statusColumn.setCellFactory(column -> new TableCell<Customer, String>() {
            private final Label badge = new Label();
            @Override protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                } else {
                    badge.setText(status);
                    badge.getStyleClass().setAll("customer-status-badge", "status-" + status.toLowerCase().replace(" ", "-"));
                    setGraphic(badge);
                }
            }
        });

        actionsColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        actionsColumn.setCellFactory(column -> new TableCell<Customer, Customer>() {
            private final Button toggleBtn = new Button();
            {
                toggleBtn.getStyleClass().add("customer-action-btn");
                toggleBtn.setOnAction(e -> {
                    Customer old = getItem();
                    if (old != null) {
                        String newStatus = old.status().equals("Active") ? "Inactive" : "Active";
                        toggleBtn.setDisable(true); // Prevent double clicks
                        customerService.updateCustomerStatus(old.id(), newStatus).thenAccept(updatedCust -> {
                            Platform.runLater(() -> {
                                int idx = allCustomers.indexOf(old);
                                if (idx >= 0) allCustomers.set(idx, updatedCust);
                                updateSummaryCounters();
                            });
                        }).exceptionally(ex -> {
                            ex.printStackTrace();
                            Platform.runLater(() -> toggleBtn.setDisable(false));
                            return null;
                        });
                    }
                });
            }
            @Override protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    if ("Active".equals(item.status())) {
                        toggleBtn.setText("Deactivate");
                        toggleBtn.setTooltip(new Tooltip("Mark customer as inactive"));
                    } else {
                        toggleBtn.setText("Activate");
                        toggleBtn.setTooltip(new Tooltip("Mark customer as active"));
                    }
                    setGraphic(toggleBtn);
                }
            }
        });
    }

                
    

         
    @FXML
    private void handleAddCustomer() {
        Dialog<Customer> dialog = new Dialog<>();
        dialog.setTitle("Add New Customer");
        dialog.setHeaderText("Enter new customer details");
        dialog.initOwner(customersTable.getScene().getWindow());
        
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Phone:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String id = "CUST-" + (1000 + allCustomers.size() + 1);
                return new Customer(id, nameField.getText(), phoneField.getText(), 0, 0.0, "Active", passwordField.getText());
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(customer -> {
            customerService.addCustomer(customer).thenAccept(savedCust -> {
                Platform.runLater(() -> {
                    allCustomers.add(savedCust);
                    updateSummaryCounters();
                });
            }).exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });
        });
    }

}
