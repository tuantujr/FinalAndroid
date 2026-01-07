package com.example.ute.models.response;

import com.example.ute.models.Review;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class ReviewListResponse {
    private boolean success;
    private String message;
    private List<Review> data;
    @SerializedName("metadata")
    private Map<String, Object> metadata;

    // Constructor
    public ReviewListResponse() {
    }

    public ReviewListResponse(boolean success, String message, List<Review> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Review> getData() {
        return data;
    }

    public void setData(List<Review> data) {
        this.data = data;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
