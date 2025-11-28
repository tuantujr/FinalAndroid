package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Product {
    @SerializedName("_id")
    private String id;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("description")
    private String description;
    
    @SerializedName("picUrl")
    private List<String> picUrl;
    
    @SerializedName("price")
    private double price;
    
    @SerializedName("oldPrice")
    private double oldPrice;
    
    @SerializedName("rating")
    private double rating;
    
    @SerializedName("categoryId")
    private String categoryId;

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<String> getPicUrl() { return picUrl; }
    public double getPrice() { return price; }
    public double getOldPrice() { return oldPrice; }
    public double getRating() { return rating; }
    public String getCategoryId() { return categoryId; }
}
