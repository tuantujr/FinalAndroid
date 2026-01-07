package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("orderCode")
    private String orderCode;
    
    @SerializedName("userId")
    private Long userId;
    
    @SerializedName("items")
    private List<OrderItem> items;
    
    @SerializedName("totalAmount")
    private Double totalAmount;
    
    @SerializedName("discount")
    private Double discount;
    
    @SerializedName("shippingFee")
    private Double shippingFee;
    
    @SerializedName("finalAmount")
    private Double finalAmount;
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("paymentMethod")
    private String paymentMethod;
    
    @SerializedName("recipientName")
    private String recipientName;
    
    @SerializedName("phoneNumber")
    private String phoneNumber;
    
    @SerializedName("streetAddress")
    private String streetAddress;
    
    @SerializedName("ward")
    private String ward;
    
    @SerializedName("district")
    private String district;
    
    @SerializedName("city")
    private String city;
    
    @SerializedName("note")
    private String note;
    
    @SerializedName("voucherCode")
    private String voucherCode;
    
    @SerializedName("shippingInfo")
    private ShippingInfo shippingInfo;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    @SerializedName("updatedAt")
    private String updatedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
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

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (streetAddress != null) sb.append(streetAddress);
        if (ward != null) sb.append(", ").append(ward);
        if (district != null) sb.append(", ").append(district);
        if (city != null) sb.append(", ").append(city);
        return sb.toString();
    }
    
    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
    
    // Nested ShippingInfo class
    public static class ShippingInfo {
        @SerializedName("recipientName")
        private String recipientName;
        
        @SerializedName("phoneNumber")
        private String phoneNumber;
        
        @SerializedName("streetAddress")
        private String streetAddress;
        
        @SerializedName("city")
        private String city;
        
        @SerializedName("ward")
        private String ward;
        
        @SerializedName("email")
        private String email;
        
        public String getRecipientName() {
            return recipientName;
        }

        public void setRecipientName(String recipientName) {
            this.recipientName = recipientName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getStreetAddress() {
            return streetAddress;
        }

        public void setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
