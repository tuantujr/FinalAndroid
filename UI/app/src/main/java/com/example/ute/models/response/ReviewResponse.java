package com.example.ute.models.response;

import com.example.ute.models.Review;

public class ReviewResponse {
    private boolean success;
    private String message;
    private Review data;

    // Constructor
    public ReviewResponse() {
    }

    public ReviewResponse(boolean success, String message, Review data) {
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

    public Review getData() {
        return data;
    }

    public void setData(Review data) {
        this.data = data;
    }
}
