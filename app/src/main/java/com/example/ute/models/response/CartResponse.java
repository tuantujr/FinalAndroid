package com.example.ute.models.response;

import com.example.ute.models.Cart;
import com.google.gson.annotations.SerializedName;

/**
 * Cart Response matching backend format:
 * { "success": true, "message": "...", "data": { cart object } }
 */
public class CartResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private Cart data;

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

    public Cart getData() {
        return data;
    }

    public void setData(Cart data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success != null && success;
    }
}
