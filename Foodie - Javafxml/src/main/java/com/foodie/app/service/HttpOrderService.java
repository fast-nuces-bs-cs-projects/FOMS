package com.foodie.app.service;

import com.foodie.app.api.ApiClient;
import com.foodie.app.model.OrdersData.OrderRow;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HttpOrderService implements OrderService {
    @Override
    public CompletableFuture<List<OrderRow>> getOrders() {
        return ApiClient.get("/orders", OrderRow[].class).thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<List<OrderRow>> getIncomingOrders() {
        return ApiClient.get("/orders/incoming", OrderRow[].class).thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<Void> updateOrderStatus(String id, String status) {
        record StatusUpdate(String status) {}
        return ApiClient.put("/orders/" + id + "/status", new StatusUpdate(status), Void.class);
    }
}
