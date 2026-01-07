package com.example.ute.models.response;

import com.example.ute.models.User;
import com.google.gson.annotations.SerializedName;

/**
 * User Response wrapper matching backend format:
 * { "success": true, "data": { user object } }
 */
public class UserDataResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private User data;

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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success != null && success;
    }
}
