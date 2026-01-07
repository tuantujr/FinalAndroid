package com.example.ute.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Response wrapper for single address endpoint (add/update/get)
 */
public class AddressResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private com.example.ute.models.Address data;

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

    public com.example.ute.models.Address getData() {
        return data;
    }

    public void setData(com.example.ute.models.Address data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success != null && success;
    }
}
