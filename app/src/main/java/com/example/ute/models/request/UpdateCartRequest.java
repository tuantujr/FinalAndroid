package com.example.ute.models.request;

import com.google.gson.annotations.SerializedName;

public class UpdateCartRequest {
    @SerializedName("quantity")
    private Integer quantity;

    public UpdateCartRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
