package com.foodie.app.models;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("order_id")
    private String id;
    
    // Server doesn't send title, will be null unless mapped
    private String title;
    
    @SerializedName("items_summary")
    private String items;
    
    @SerializedName("date_time")
    private String time;
    
    @SerializedName("total_amount")
    private String price;
    
    @SerializedName("status")
    private String status;
    
    private String statusColor;
    private int bgResId;

    public Order(String id, String title, String items, String time, String price, String status, String statusColor, int bgResId) {
        this.id = id;
        this.title = title;
        this.items = items;
        this.time = time;
        this.price = price;
        this.status = status;
        this.statusColor = statusColor;
        this.bgResId = bgResId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getItems() { return items; }
    public String getTime() { return time; }
    public String getPrice() { return price; }
    public String getStatus() { return status; }
    public String getStatusColor() { return statusColor; }
    public int getBgResId() { return bgResId; }
}
