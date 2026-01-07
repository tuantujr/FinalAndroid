package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

public class ProductImage {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("imageUrl")
    private String imageUrl;
    
    @SerializedName("isPrimary")
    private Boolean isPrimary;
    
    @SerializedName("displayOrder")
    private Integer displayOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
