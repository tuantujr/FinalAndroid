package com.example.ute.models.request;

import com.google.gson.annotations.SerializedName;

public class ValidateVoucherRequest {
    @SerializedName("code")
    private String code;

    @SerializedName("orderTotal")
    private Long orderTotal;

    @SerializedName("userId")
    private Long userId;

    public ValidateVoucherRequest(String code, Long orderTotal) {
        this.code = code;
        this.orderTotal = orderTotal;
        this.userId = null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
