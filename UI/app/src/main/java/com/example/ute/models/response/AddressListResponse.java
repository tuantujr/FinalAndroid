package com.example.ute.models.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Response wrapper for address list endpoints
 */
public class AddressListResponse {
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private List<com.example.ute.models.Address> data;

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

    public List<com.example.ute.models.Address> getData() {
        return data;
    }

    public void setData(List<com.example.ute.models.Address> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success != null && success;
    }
}
