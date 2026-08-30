package com.foodie.app.models.responses;

public class OrderResponse {
    private String message;
    private int order_id;

    public OrderResponse(String message, int order_id) {
        this.message = message;
        this.order_id = order_id;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getOrderId() { return order_id; }
    public void setOrderId(int order_id) { this.order_id = order_id; }
}
