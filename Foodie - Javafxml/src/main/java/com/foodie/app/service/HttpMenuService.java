package com.foodie.app.service;

import com.foodie.app.api.ApiClient;
import com.foodie.app.model.MenuData.MenuItem;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HttpMenuService implements MenuService {
    @Override
    public CompletableFuture<List<MenuItem>> getMenuItems() {
        return ApiClient.get("/api/menu", MenuItem[].class).thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<MenuItem> addMenuItem(MenuItem item) {
        return ApiClient.post("/api/menu", item, MenuItem.class);
    }

    @Override
    public CompletableFuture<MenuItem> updateMenuItem(String id, MenuItem item) {
        return ApiClient.put("/api/menu/" + id, item, MenuItem.class);
    }

    @Override
    public CompletableFuture<Void> deleteMenuItem(String id) {
        return ApiClient.delete("/api/menu/" + id);
    }
}
