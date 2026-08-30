package com.foodie.app.models.requests;

import java.util.List;

public class PlaceOrderRequest {
    private String delivery_address;
    private String payment_method;
    private List<CartItemRequest> cart_items;

    public PlaceOrderRequest(String delivery_address, String payment_method, List<CartItemRequest> cart_items) {
        this.delivery_address = delivery_address;
        this.payment_method = payment_method;
        this.cart_items = cart_items;
    }

    public String getDeliveryAddress() { return delivery_address; }
    public void setDeliveryAddress(String delivery_address) { this.delivery_address = delivery_address; }
    public String getPaymentMethod() { return payment_method; }
    public void setPaymentMethod(String payment_method) { this.payment_method = payment_method; }
    public List<CartItemRequest> getCartItems() { return cart_items; }
    public void setCartItems(List<CartItemRequest> cart_items) { this.cart_items = cart_items; }
}
