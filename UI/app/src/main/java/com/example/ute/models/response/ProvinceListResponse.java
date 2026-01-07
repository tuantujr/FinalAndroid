package com.example.ute.models.response;

import com.example.ute.models.Province;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProvinceListResponse {
    @SerializedName("success")
    private boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private List<Province> data;

    public ProvinceListResponse() {
    }

    public ProvinceListResponse(boolean success, String message, List<Province> data) {
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

    public List<Province> getData() {
        return data;
    }

    public void setData(List<Province> data) {
        this.data = data;
    }
}
