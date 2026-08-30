package com.foodie.app.model;
public record Customer(String id, String name, String phone, int totalOrders, double totalSpent, String status, String password) {}
