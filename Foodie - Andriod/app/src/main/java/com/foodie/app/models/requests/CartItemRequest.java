package com.foodie.app.models.requests;

public class CartItemRequest {
    private int food_item_id;
    private int quantity;

    public CartItemRequest(int food_item_id, int quantity) {
        this.food_item_id = food_item_id;
        this.quantity = quantity;
    }

    public int getFoodItemId() { return food_item_id; }
    public void setFoodItemId(int food_item_id) { this.food_item_id = food_item_id; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
