package com.foodie.app.service;

import com.foodie.app.model.MenuData.MenuItem;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MenuService {
    CompletableFuture<List<MenuItem>> getMenuItems();
    CompletableFuture<MenuItem> addMenuItem(MenuItem item);
    CompletableFuture<MenuItem> updateMenuItem(String id, MenuItem item);
    CompletableFuture<Void> deleteMenuItem(String id);
}
