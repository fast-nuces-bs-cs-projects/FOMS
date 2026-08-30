package com.foodie.app.service;

import com.foodie.app.model.OrdersData.OrderRow;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    CompletableFuture<List<OrderRow>> getOrders();
    CompletableFuture<List<OrderRow>> getIncomingOrders();
    CompletableFuture<Void> updateOrderStatus(String id, String status);
}
