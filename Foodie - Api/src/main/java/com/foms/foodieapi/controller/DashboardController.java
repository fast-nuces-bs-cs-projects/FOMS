package com.foms.foodieapi.controller;

import com.foms.foodieapi.model.Order;
import com.foms.foodieapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public Map<String, Object> getDashboardData() {
        List<Order> allOrders = orderRepository.findAll();

        long totalOrders = allOrders.size();
        long pendingOrders = allOrders.stream().filter(o -> "PENDING".equalsIgnoreCase(o.getStatus())).count();
        long completedOrders = allOrders.stream().filter(o -> "COMPLETED".equalsIgnoreCase(o.getStatus())).count();
        
        double revenue = allOrders.stream()
                .filter(o -> !"REJECTED".equalsIgnoreCase(o.getStatus()))
                .mapToDouble(Order::getTotalAmount)
                .sum();

        Map<String, Object> summary = Map.of(
                "totalOrders", totalOrders,
                "pendingOrders", pendingOrders,
                "revenue", revenue,
                "completedOrders", completedOrders
        );

        // Mocking weekly orders for now, as calculating this requires historical seed data
        List<Map<String, Object>> weeklyOrders = List.of(
                Map.of("day", "Mon", "count", 45),
                Map.of("day", "Tue", "count", 52),
                Map.of("day", "Wed", "count", 38),
                Map.of("day", "Thu", "count", 65),
                Map.of("day", "Fri", "count", 89),
                Map.of("day", "Sat", "count", 110),
                Map.of("day", "Sun", "count", 95)
        );

        List<Map<String, Object>> recentOrders = allOrders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(5)
                .map(order -> Map.<String, Object>of(
                        "id", "ORD-" + order.getId(),
                        "customer", order.getUser().getName(),
                        "amount", order.getTotalAmount(),
                        "status", order.getStatus()
                ))
                .collect(Collectors.toList());

        return Map.of(
                "summary", summary,
                "weeklyOrders", weeklyOrders,
                "recentOrders", recentOrders
        );
    }
}
