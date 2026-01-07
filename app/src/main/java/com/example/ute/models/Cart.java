package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("userId")
    private Long userId;
    
    @SerializedName("items")
    private List<CartItem> items;
    
    @SerializedName("totalAmount")
    private Double totalAmount;
    
    @SerializedName("discount")
    private Double discount;
    
    @SerializedName("voucherCode")
    private String voucherCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
    
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}
