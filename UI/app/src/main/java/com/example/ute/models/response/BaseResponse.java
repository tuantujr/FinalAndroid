package com.example.ute.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Base Response wrapper matching backend format:
 * { "success": true/false, "message": "...", "data": ... }
 */
public class BaseResponse<T> {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success != null && success;
    }
}
