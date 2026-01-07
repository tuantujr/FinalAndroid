package com.example.ute.models.response;

import com.example.ute.models.User;
import com.google.gson.annotations.SerializedName;

/**
 * Auth Response wrapper matching backend format:
 * { "success": true, "message": "...", "data": { "accessToken": "...", "user": {...} } }
 */
public class AuthResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private AuthData data;
    
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

    public AuthData getData() {
        return data;
    }

    public void setData(AuthData data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success != null && success;
    }
    
    // Convenience methods
    public String getAccessToken() {
        return data != null ? data.getAccessToken() : null;
    }
    
    public String getRefreshToken() {
        return data != null ? data.getRefreshToken() : null;
    }
    
    public User getUser() {
        return data != null ? data.getUser() : null;
    }
    
    /**
     * Inner class for the "data" field
     */
    public static class AuthData {
        @SerializedName("accessToken")
        private String accessToken;
        
        @SerializedName("refreshToken")
        private String refreshToken;
        
        @SerializedName("user")
        private User user;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
