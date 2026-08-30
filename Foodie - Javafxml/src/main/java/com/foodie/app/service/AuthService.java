package com.foodie.app.service;

import java.util.concurrent.CompletableFuture;

public interface AuthService {
    CompletableFuture<String> login(String email, String password);
    CompletableFuture<Void> changePassword(String currentPassword, String newPassword);
}
