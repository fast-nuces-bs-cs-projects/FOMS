package com.foodie.app.service;

import com.foodie.app.model.DashboardData;
import com.foodie.app.model.DashboardData.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MockDashboardDataProvider implements DashboardDataProvider {
    @Override
    public CompletableFuture<DashboardData> loadDashboard() {
        return CompletableFuture.completedFuture(new DashboardData(
                new Summary(145, 12, new BigDecimal("4560.50"), 133),
                List.of(
                        new DailyOrders("Mon", 45),
                        new DailyOrders("Tue", 52),
                        new DailyOrders("Wed", 38),
                        new DailyOrders("Thu", 65),
                        new DailyOrders("Fri", 89),
                        new DailyOrders("Sat", 110),
                        new DailyOrders("Sun", 95)
                ),
                List.of(
                        new Order("ORD-2023", "Nimal Perera", OrderStatus.PENDING, new BigDecimal("1250.00")),
                        new Order("ORD-2024", "Kavindu Silva", OrderStatus.PREPARING, new BigDecimal("850.00")),
                        new Order("ORD-2025", "Samanthi Fernando", OrderStatus.READY, new BigDecimal("2400.00")),
                        new Order("ORD-2026", "Dilshan Rodrigo", OrderStatus.COMPLETED, new BigDecimal("650.00")),
                        new Order("ORD-2027", "Tharindi Jayawardena", OrderStatus.COMPLETED, new BigDecimal("3200.00"))
                ),
                List.of(),
                List.of()
        ));
    }
}
