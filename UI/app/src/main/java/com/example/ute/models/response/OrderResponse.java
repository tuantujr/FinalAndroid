package com.example.ute.models.response;

import com.example.ute.models.Order;

public class OrderResponse {
    private boolean success;
    private String message;
    private Order data;

    // Constructor
    public OrderResponse() {
    }

    public OrderResponse(boolean success, String message, Order data) {
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

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }
}
