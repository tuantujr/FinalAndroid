package com.example.ute.models.response;

import com.example.ute.models.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Category List Response matching backend format:
 * { "success": true, "message": "...", "data": [ categories ] }
 */
public class CategoryListResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private List<Category> data;

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

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success != null && success;
    }
}
