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

    /**
     * Format full address for display
     */
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        
        if (streetAddress != null && !streetAddress.isEmpty()) {
            sb.append(streetAddress);
        }
        
        if (ward != null && !ward.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(ward);
        }
        
        if (province != null && !province.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(province);
        }
        
        return sb.toString();
    }
}
