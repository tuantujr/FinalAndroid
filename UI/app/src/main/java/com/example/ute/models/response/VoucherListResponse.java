package com.example.ute.models.response;

import com.example.ute.models.response.VoucherResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoucherListResponse {
    
    @SerializedName("success")
    private boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private List<VoucherResponse> data;
    
    public VoucherListResponse() {
    }
    
    public VoucherListResponse(boolean success, String message, List<VoucherResponse> data) {
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
    
    public List<VoucherResponse> getData() {
        return data;
    }
    
    public void setData(List<VoucherResponse> data) {
        this.data = data;
    }
}
