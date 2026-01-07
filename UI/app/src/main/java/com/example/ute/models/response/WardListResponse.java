package com.example.ute.models.response;

import com.example.ute.models.Ward;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WardListResponse {
    @SerializedName("success")
    private boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private List<Ward> data;

    public WardListResponse() {
    }

    public WardListResponse(boolean success, String message, List<Ward> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

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

    public List<Ward> getData() {
        return data;
    }

    public void setData(List<Ward> data) {
        this.data = data;
    }
}
