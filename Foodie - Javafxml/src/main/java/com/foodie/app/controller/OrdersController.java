package com.foodie.app.controller;

import com.foodie.app.model.DashboardData.OrderStatus;
import com.foodie.app.model.OrdersData.OrderRow;
import com.foodie.app.service.OrderService;
import com.foodie.app.service.ServiceLocator;
import javafx.application.Platform;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.NumberFormat;
import java.util.Locale;

public class OrdersController {
        @FXML private Label totalOrdersValue;
    @FXML private Label pendingOrdersValue;
    @FXML private Label preparingOrdersValue;
    @FXML private Label readyOrdersValue;
    @FXML private TextField searchField;
    @FXML private TableView<OrderRow> ordersTable;
    @FXML private TableColumn<OrderRow, OrderRow> idColumn;
    @FXML private TableColumn<OrderRow, OrderRow> customerColumn;
    @FXML private TableColumn<OrderRow, OrderRow> itemsColumn;
    @FXML private TableColumn<OrderRow, String> typeColumn;
    @FXML private TableColumn<OrderRow, String> paymentColumn;
    @FXML private TableColumn<OrderRow, String> amountColumn;
    @FXML private TableColumn<OrderRow, OrderStatus> statusColumn;
    @FXML private TableColumn<OrderRow, String> timeColumn;
    @FXML private TableColumn<OrderRow, OrderRow> actionsColumn;
    @FXML private HBox incomingOrderCard;
    @FXML private Label incomingOrderId;
    @FXML private Label incomingCustomer;
    @FXML private Label incomingItems;
    @FXML private Label incomingPayment;
    @FXML private Label incomingAmount;

    private final OrderService orderService = ServiceLocator.getOrderService();
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
    private FilteredList<OrderRow> filteredOrders;
    private ObservableList<OrderRow> acceptedOrders;
    private OrderRow mockIncomingOrder;
    private OrderStatus activeStatus;
        
    @FXML
    private void initialize() {
        configureTable();
        acceptedOrders = FXCollections.observableArrayList();
        filteredOrders = new FilteredList<>(acceptedOrders);
        ordersTable.setItems(filteredOrders);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        
        if (incomingOrderCard != null) {
            incomingOrderCard.setVisible(false);
            incomingOrderCard.setManaged(false);
        }

        loadOrders();
        startPolling();
    }

    private Timeline pollingTimeline;

    private void startPolling() {
        pollingTimeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> loadOrders()));
        pollingTimeline.setCycleCount(Timeline.INDEFINITE);
        pollingTimeline.play();
    }


    private void loadOrders() {
        orderService.getOrders().thenAccept(orders -> Platform.runLater(() -> {
            acceptedOrders.setAll(orders);
            totalOrdersValue.setText(String.format("%,d", orders.size()));
            long pending = orders.stream().filter(o -> o.status() == OrderStatus.PENDING).count();
            pendingOrdersValue.setText(String.format("%,d", pending));
            long preparing = orders.stream().filter(o -> o.status() == OrderStatus.PREPARING).count();
            preparingOrdersValue.setText(String.format("%,d", preparing));
            long ready = orders.stream().filter(o -> o.status() == OrderStatus.READY).count();
            readyOrdersValue.setText(String.format("%,d", ready));
        }));

        orderService.getIncomingOrders().thenAccept(incoming -> Platform.runLater(() -> {
            if (!incoming.isEmpty()) {
                mockIncomingOrder = incoming.get(0);
                showIncomingOrder(mockIncomingOrder);
            }
        }));
    }

    private void configureTable() {
        ordersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        idColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        customerColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        itemsColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        typeColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().type() != null ? cell.getValue().type().displayName() : "Unknown"));
        paymentColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().payment()));
        amountColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().amount() != null ? currency.format(cell.getValue().amount()) : currency.format(java.math.BigDecimal.ZERO)));
        statusColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().status()));
        timeColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().time()));
        actionsColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));

        idColumn.setCellFactory(column -> new TwoPartCell<>(row -> row.id(), row -> row.newOrder() ? "NEW" : ""));
        customerColumn.setCellFactory(column -> new TwoPartCell<>(OrderRow::customer, OrderRow::phone));
        itemsColumn.setCellFactory(column -> new TwoPartCell<>(OrderRow::items, OrderRow::extras));
        statusColumn.setCellFactory(column -> new StatusCell());
        actionsColumn.setCellFactory(column -> new ActionsCell());
    }

    @FXML
    private void handleFilter(javafx.event.ActionEvent event) {
        ToggleButton button = (ToggleButton) event.getSource();
        String filter = String.valueOf(button.getUserData());
        activeStatus = "ALL".equals(filter) ? null : OrderStatus.valueOf(filter);
        applyFilters();
    }

    private void applyFilters() {
        String query = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        filteredOrders.setPredicate(order -> {
            boolean statusMatches = activeStatus == null || order.status() == activeStatus;
            boolean searchMatches = query.isEmpty()
                    || order.id().toLowerCase().contains(query)
                    || order.customer().toLowerCase().contains(query)
                    || order.phone().contains(query)
                    || order.items().toLowerCase().contains(query);
            return statusMatches && searchMatches;
        });
    }

    @FXML
    private void handleAcceptIncoming() {
        if (mockIncomingOrder != null) {
            orderService.updateOrderStatus(mockIncomingOrder.id(), "ACCEPTED").thenAccept(v -> Platform.runLater(() -> {
                if (incomingOrderCard != null) {
                    incomingOrderCard.setVisible(false);
                    incomingOrderCard.setManaged(false);
                }
                mockIncomingOrder = null;
                loadOrders(); // Refresh table entirely from API
            }));
        }
    }

    @FXML
    private void handleRejectIncoming() {
        if (mockIncomingOrder != null) {
            orderService.updateOrderStatus(mockIncomingOrder.id(), "REJECTED").thenAccept(v -> Platform.runLater(() -> {
                if (incomingOrderCard != null) {
                    incomingOrderCard.setVisible(false);
                    incomingOrderCard.setManaged(false);
                }
                mockIncomingOrder = null;
                loadOrders(); // Refresh table entirely from API
            }));
        }
    }

    @FXML
    private void handleSimulateIncoming() {
        if (mockIncomingOrder != null) {
            showIncomingOrder(mockIncomingOrder);
        }
    }

    private boolean isModalShowing = false;

    private void showIncomingOrder(OrderRow order) {
        if (order == null || isModalShowing) return;
        isModalShowing = true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New Incoming Order!");
        alert.setHeaderText("Order " + order.id() + " from " + order.customer());
        alert.setContentText(
                "Phone: " + (order.phone() != null ? order.phone() : "N/A") + "\n" +
                "Items: " + (order.items() != null && !order.items().isBlank() ? order.items() : "Unknown") + "\n" +
                (order.extras() == null || order.extras().isBlank() ? "" : "Extras: " + order.extras() + "\n") +
                "Payment: " + (order.type() != null ? order.type().displayName() : "Unknown") + " - " + (order.payment() != null ? order.payment() : "Unknown") + "\n" +
                "Amount: " + (order.amount() != null ? currency.format(order.amount()) : currency.format(java.math.BigDecimal.ZERO))
        );

        ButtonType acceptBtn = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        ButtonType rejectBtn = new ButtonType("Reject", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(acceptBtn, rejectBtn);

        alert.showAndWait().ifPresent(type -> {
            isModalShowing = false;
            if (type == acceptBtn) {
                handleAcceptIncoming();
            } else if (type == rejectBtn) {
                handleRejectIncoming();
            }
        });
    }


    private long parseCount(Label label) {
        try {
            return Long.parseLong(label.getText().replace(",", "").replace("$", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void showOrder(OrderRow order) {
        if (order == null) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order " + order.id());
        alert.setHeaderText(order.customer() + " - " + (order.status() != null ? order.status().displayName() : "Unknown Status"));
        alert.setContentText(
                "Phone: " + (order.phone() != null ? order.phone() : "N/A") + "\n" +
                "Items: " + (order.items() != null && !order.items().isBlank() ? order.items() : "Unknown") + "\n" +
                (order.extras() == null || order.extras().isBlank() ? "" : "Extras: " + order.extras() + "\n") +
                "Type: " + (order.type() != null ? order.type().displayName() : "Unknown") + "\n" +
                "Payment: " + (order.payment() != null ? order.payment() : "Unknown") + "\n" +
                "Amount: " + (order.amount() != null ? currency.format(order.amount()) : currency.format(java.math.BigDecimal.ZERO))
        );
        alert.showAndWait();
    }

    private class TwoPartCell<T> extends TableCell<OrderRow, T> {
        private final javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(2);
        private final Label l1 = new Label();
        private final Label l2 = new Label();
        private final java.util.function.Function<OrderRow, String> f1;
        private final java.util.function.Function<OrderRow, String> f2;

        public TwoPartCell(java.util.function.Function<OrderRow, String> f1, java.util.function.Function<OrderRow, String> f2) {
            this.f1 = f1;
            this.f2 = f2;
            l1.setStyle("-fx-font-weight: bold;");
            l2.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");
            box.getChildren().addAll(l1, l2);
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
            } else {
                OrderRow row = getTableRow().getItem();
                l1.setText(f1.apply(row));
                String t2 = f2.apply(row);
                if (t2 == null || t2.trim().isEmpty()) {
                    l2.setText("");
                    l2.setManaged(false);
                    l2.setVisible(false);
                } else {
                    l2.setText(t2);
                    l2.setManaged(true);
                    l2.setVisible(true);
                }
                setGraphic(box);
            }
        }
    }

    private class StatusCell extends TableCell<OrderRow, OrderStatus> {
        private final Label badge = new Label();
        @Override protected void updateItem(OrderStatus status, boolean empty) {
            super.updateItem(status, empty);
            if (empty || status == null) { setGraphic(null); }
            else {
                badge.setText(status.name());
                badge.getStyleClass().setAll("order-status-badge", "status-" + status.name().toLowerCase());
                setGraphic(badge);
            }
        }
    }

    private class ActionsCell extends TableCell<OrderRow, OrderRow> {
        private final Button viewButton = new Button("View");
        public ActionsCell() {
            viewButton.getStyleClass().add("action-view-btn");
            viewButton.setTooltip(new javafx.scene.control.Tooltip("View order details"));
            viewButton.setOnAction(e -> showOrder(getItem()));
        }
        @Override protected void updateItem(OrderRow row, boolean empty) {
            super.updateItem(row, empty);
            setGraphic(empty || row == null ? null : viewButton);
        }
    }
}






