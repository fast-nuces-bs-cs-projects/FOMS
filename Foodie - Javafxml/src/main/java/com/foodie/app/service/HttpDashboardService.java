package com.foodie.app.service;

import com.foodie.app.api.ApiClient;
import com.foodie.app.model.DashboardData;
import java.util.concurrent.CompletableFuture;

public class HttpDashboardService implements DashboardDataProvider {
    @Override
    public CompletableFuture<DashboardData> loadDashboard() {
        return ApiClient.get("/dashboard", DashboardData.class);
    }
}
