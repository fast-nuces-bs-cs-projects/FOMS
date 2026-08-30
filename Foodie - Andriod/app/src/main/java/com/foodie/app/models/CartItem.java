package com.foodie.app.models;

public class CartItem {
    private String id;
    private String title;
    private String desc;
    private String price;
    private int quantity = 1;
    private int imageResId;
    private String imageUrl;

    public CartItem(String id, String title, String desc, String price, int imageResId, String imageUrl) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.imageResId = imageResId;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDesc() { return desc; }
    public String getPrice() { return price; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getImageResId() { return imageResId; }
    public String getImageUrl() { return imageUrl; }
}
