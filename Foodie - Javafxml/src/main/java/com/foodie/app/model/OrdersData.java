package com.foodie.app.model;

import com.foodie.app.model.DashboardData.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrdersData(
        long totalOrders,
        long pendingOrders,
        long preparingOrders,
        long readyOrders,
        OrderRow incomingOrder,
        List<OrderRow> orders
) {
    public record OrderRow(
            String id,
            boolean newOrder,
            String customer,
            String phone,
            String items,
            String extras,
            OrderType type,
            String payment,
            BigDecimal amount,
            OrderStatus status,
            String time
    ) {}

    public enum OrderType {
        DELIVERY("Delivery"),
        PICKUP("Pickup");

        private final String displayName;

        OrderType(String displayName) {
            this.displayName = displayName;
        }

        public String displayName() {
            return displayName;
        }
    }
}
