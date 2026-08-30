package com.foodie.app.service;

import com.foodie.app.api.ApiClient;
import com.foodie.app.api.ApiConfig;
import java.util.concurrent.CompletableFuture;

public class HttpAuthService implements AuthService {
    record LoginRequest(String email, String password) {}
    record LoginResponse(String token) {}
    record ChangePasswordRequest(String current_password, String new_password) {}

    @Override
    public CompletableFuture<String> login(String email, String password) {
        return ApiClient.post("/auth/login", new LoginRequest(email, password), LoginResponse.class)
                .thenApply(res -> {
                    ApiConfig.setJwtToken(res.token());
                    return res.token();
                });
    }

    @Override
    public CompletableFuture<Void> changePassword(String currentPassword, String newPassword) {
        return ApiClient.post("/auth/change-password", new ChangePasswordRequest(currentPassword, newPassword), Void.class);
    }
}
