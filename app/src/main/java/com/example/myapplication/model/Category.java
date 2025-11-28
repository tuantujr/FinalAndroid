package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("_id")
    private String id;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("picUrl")
    private String picUrl;

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getPicUrl() { return picUrl; }
}
