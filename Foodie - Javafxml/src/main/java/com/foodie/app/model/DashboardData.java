package com.foodie.app.model;

import java.math.BigDecimal;
import java.util.List;

public record DashboardData(
        Summary summary,
        List<DailyOrders> weeklyOrders,
        List<Order> recentOrders,
        List<StatusSummary> orderStatuses,
        List<TopItem> topSellingItems
) {
    public record Summary(long totalOrders, long pendingOrders, BigDecimal revenue, long completedOrders) {}

    public record DailyOrders(String day, int count) {}

    public record Order(String id, String customer, OrderStatus status, BigDecimal amount) {}

    public record StatusSummary(OrderStatus status, long count, double percentage) {}

    public record TopItem(String name, long orders) {}

    public enum OrderStatus {
        PENDING("Pending", "status-pending"),
        PREPARING("Preparing", "status-preparing"),
        READY("Ready", "status-ready"),
        COMPLETED("Completed", "status-completed"),
        CANCELLED("Cancelled", "status-cancelled");

        private final String displayName;
        private final String cssClass;

        OrderStatus(String displayName, String cssClass) {
            this.displayName = displayName;
            this.cssClass = cssClass;
        }

        public String displayName() {
            return displayName;
        }

        public String cssClass() {
            return cssClass;
        }
    }
}
