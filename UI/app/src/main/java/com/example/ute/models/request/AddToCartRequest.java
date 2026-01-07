package com.example.ute.models.request;

import com.google.gson.annotations.SerializedName;

public class AddToCartRequest {
    @SerializedName("productId")
    private Long productId;
    
    @SerializedName("quantity")
    private Integer quantity;

    public AddToCartRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
