package com.foodie.app.controller;

import com.foodie.app.model.DashboardData;
import com.foodie.app.model.DashboardData.Order;
import com.foodie.app.model.DashboardData.OrderStatus;
import com.foodie.app.service.DashboardDataProvider;
import com.foodie.app.service.ServiceLocator;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DashboardController {
        @FXML private LineChart<String, Number> orderChart;
    @FXML private TableView<Order> recentOrdersTable;
    @FXML private TableColumn<Order, String> orderIdColumn;
    @FXML private TableColumn<Order, String> customerColumn;
    @FXML private TableColumn<Order, OrderStatus> statusColumn;
    @FXML private TableColumn<Order, String> amountColumn;
    @FXML private Label totalOrdersValue;
    @FXML private Label pendingOrdersValue;
    @FXML private Label revenueValue;
    @FXML private Label completedOrdersValue;

            private DashboardDataProvider dataProvider = ServiceLocator.getDashboardService();
    private final NumberFormat integerFormat = NumberFormat.getIntegerInstance(Locale.US);
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    @FXML
    private void initialize() {
        configureOrdersTable();
        loadData();
    }

    public void setDataProvider(DashboardDataProvider dataProvider) {
        this.dataProvider = dataProvider;
        if (orderChart != null) {
            loadData();
        }
    }

    private void loadData() {
        dataProvider.loadDashboard().thenAccept(data -> {
            javafx.application.Platform.runLater(() -> renderDashboard(data));
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    private void configureOrdersTable() {
        recentOrdersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        recentOrdersTable.setSelectionModel(null);

        orderIdColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().id()));
        customerColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().customer()));
        statusColumn.setCellValueFactory(cell -> new javafx.beans.property.ReadOnlyObjectWrapper<>(cell.getValue().status()));
        amountColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                currencyFormat.format(cell.getValue().amount() != null ? cell.getValue().amount().setScale(2, RoundingMode.HALF_UP) : java.math.BigDecimal.ZERO)
        ));

        statusColumn.setCellFactory(column -> new TableCell<>() {
            private final Label badge = new Label();

            @Override
            protected void updateItem(OrderStatus status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                    return;
                }
                badge.setText(status.displayName());
                badge.getStyleClass().setAll("order-status-badge", status.cssClass());
                setGraphic(badge);
                setText(null);
            }
        });
    }

    private void renderDashboard(DashboardData data) {
        totalOrdersValue.setText(integerFormat.format(data.summary().totalOrders()));
        pendingOrdersValue.setText(integerFormat.format(data.summary().pendingOrders()));
        revenueValue.setText(currencyFormat.format(data.summary().revenue()));
        completedOrdersValue.setText(integerFormat.format(data.summary().completedOrders()));

        recentOrdersTable.getItems().setAll(data.recentOrders());
        renderWeeklyOrders(data.weeklyOrders());
    }

    private void renderWeeklyOrders(List<DashboardData.DailyOrders> dailyOrders) {
        orderChart.getData().clear();
        XYChart.Series<String, Number> orders = new XYChart.Series<>();
        dailyOrders.forEach(day -> orders.getData().add(new XYChart.Data<>(day.day(), day.count())));
        orderChart.getData().add(orders);
    }

    
        }
