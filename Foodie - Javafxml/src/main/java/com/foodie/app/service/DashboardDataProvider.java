package com.foodie.app.service;

import com.foodie.app.model.DashboardData;
import java.util.concurrent.CompletableFuture;

public interface DashboardDataProvider {
    CompletableFuture<DashboardData> loadDashboard();
}
