package com.example.ute.models.request;

import com.google.gson.annotations.SerializedName;

public class CheckoutRequest {
    @SerializedName("shippingInfo")
    private ShippingInfo shippingInfo;
    
    @SerializedName("voucherCode")
    private String voucherCode;
    
    @SerializedName("paymentMethod")
    private String paymentMethod;

    public CheckoutRequest() {
    }

    public CheckoutRequest(ShippingInfo shippingInfo, String voucherCode, String paymentMethod) {
        this.shippingInfo = shippingInfo;
        this.voucherCode = voucherCode;
        this.paymentMethod = paymentMethod;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Inner class for Shipping Info
    public static class ShippingInfo {
        @SerializedName("recipientName")
        private String recipientName;
        
        @SerializedName("phoneNumber")
        private String phoneNumber;
        
        @SerializedName("email")
        private String email;
        
        @SerializedName("streetAddress")
        private String streetAddress;
        
        @SerializedName("city")
        private String city;
        
        @SerializedName("ward")
        private String ward;
        
        @SerializedName("notes")
        private String notes;

        public ShippingInfo() {
        }

        public ShippingInfo(String recipientName, String phoneNumber, String streetAddress, 
                          String city, String ward) {
            this.recipientName = recipientName;
            this.phoneNumber = phoneNumber;
            this.streetAddress = streetAddress;
            this.city = city;
            this.ward = ward;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
