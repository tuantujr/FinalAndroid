package com.example.ute.models.response;

import com.example.ute.models.Product;
import com.google.gson.annotations.SerializedName;

/**
 * Product Detail Response matching backend format:
 * { "success": true, "message": "...", "data": { product object } }
 */
public class ProductDetailResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private Product data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success != null && success;
    }
}
