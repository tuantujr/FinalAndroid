package com.example.ute.models;

import com.google.gson.annotations.SerializedName;

/**
 * Address model matching backend address structure
 */
public class Address {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("recipientName")
    private String recipientName;
    
    @SerializedName("phoneNumber")
    private String phoneNumber;
    
    @SerializedName("street")
    private String street;
    
    @SerializedName("ward")
    private String ward;
    
    @SerializedName("district")
    private String district;
    
    @SerializedName("city")
    private String city;
    
    @SerializedName("isDefault")
    private Boolean isDefault;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    @SerializedName("updatedAt")
    private String updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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

    // Helper methods
    public String getFullAddress() {
        return street + ", " + ward + ", " + district + ", " + city;
    }
}
