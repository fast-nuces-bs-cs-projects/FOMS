package com.foodie.app.models;

import com.google.gson.annotations.SerializedName;

public class FoodItem {
    @SerializedName("id")
    private String id;
    
    @SerializedName("name")
    private String title;
    
    @SerializedName("description")
    private String desc;
    
    @SerializedName("price")
    private String price;
    
    @SerializedName("imageUrl")
    private String imageUrl;
    
    private int imageResId;

    public FoodItem(String id, String title, String desc, String price, int imageResId, String imageUrl) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.imageResId = imageResId;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
