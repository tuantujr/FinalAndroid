package com.example.ute.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Cart Response matching backend format:
 * { "success": true, "message": "...", "data": { cart object or flat items } }
 */
public class CartResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success != null && success;
    }
}
