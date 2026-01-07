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
    
    @SerializedName("streetAddress")
    private String streetAddress;
    
    @SerializedName("province")
    private String province;
    
    @SerializedName("provinceCode")
    private String provinceCode;
    
    @SerializedName("ward")
    private String ward;
    
    @SerializedName("wardCode")
    private String wardCode;
    
    @SerializedName("isDefault")
    private Boolean isDefault;

    public AddressRequest() {
    }

    public AddressRequest(String recipientName, String phoneNumber, String streetAddress, String province, String provinceCode, String ward, String wardCode, Boolean isDefault) {
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.province = province;
        this.provinceCode = provinceCode;
        this.ward = ward;
        this.wardCode = wardCode;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
