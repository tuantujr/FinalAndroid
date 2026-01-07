package com.example.ute.models.request;

import com.google.gson.annotations.SerializedName;

/**
 * Request body for add/update address endpoints
 * POST /api/v1/user/addresses
 * PUT /api/v1/user/addresses/{id}
 */
public class AddressRequest {
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

    public AddressRequest() {
    }

    public AddressRequest(String recipientName, String phoneNumber, String street, String ward, String district, String city, Boolean isDefault) {
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.isDefault = isDefault;
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
}
