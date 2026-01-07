package com.example.ute.models.response;

import com.example.ute.models.Order;
import java.util.List;

public class OrderListResponse {
    private boolean success;
    private String message;
    private List<Order> data;

    // Constructor
    public OrderListResponse() {
    }

    public OrderListResponse(boolean success, String message, List<Order> data) {
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

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
