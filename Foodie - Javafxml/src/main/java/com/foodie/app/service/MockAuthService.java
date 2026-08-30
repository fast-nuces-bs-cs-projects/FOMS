package com.foodie.app.service;

import java.util.concurrent.CompletableFuture;

public class MockAuthService implements AuthService {
    @Override
    public CompletableFuture<String> login(String email, String password) {
        if ("admin@foodie.com".equals(email) && "admin123".equals(password)) {
            return CompletableFuture.completedFuture("mock-jwt-token-123");
        } else {
            return CompletableFuture.failedFuture(new RuntimeException("Invalid credentials"));
        }
    }

    @Override
    public CompletableFuture<Void> changePassword(String currentPassword, String newPassword) {
        if ("admin123".equals(currentPassword)) {
            return CompletableFuture.completedFuture(null);
        } else {
            return CompletableFuture.failedFuture(new RuntimeException("Incorrect current password"));
        }
    }
}
