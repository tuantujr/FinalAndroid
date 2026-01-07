package com.example.ute.models.response;

import com.example.ute.models.Brand;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BrandResponse {
    @SerializedName("data")
    private List<Brand> data;
    
    @SerializedName("success")
    private boolean success;
    
    @SerializedName("message")
    private String message;

    public List<Brand> getData() {
        return data;
    }

    public void setData(List<Brand> data) {
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
}
