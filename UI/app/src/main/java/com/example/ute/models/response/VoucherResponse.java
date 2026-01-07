package com.example.ute.models.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class VoucherResponse implements Serializable {
    
    @SerializedName("id")
    private Long id;
    
    @SerializedName("code")
    private String code;
    
    @SerializedName("description")
    private String description;
    
    @SerializedName("discountType")
    private String discountType; // FIXED_AMOUNT or PERCENTAGE
    
    @SerializedName("discountValue")
    private Double discountValue;
    
    @SerializedName("usageLimit")
    private Integer usageLimit;
    
    @SerializedName("maxUsage")
    private Integer maxUsage;
    
    @SerializedName("usedCount")
    private Integer usedCount;
    
    @SerializedName("minOrderValue")
    private Double minOrderValue;
    
    @SerializedName("maxDiscountAmount")
    private Double maxDiscountAmount;
    
    @SerializedName("endDate")
    private String endDate;
    
    @SerializedName("expiryDate")
    private String expiryDate;
    
    @SerializedName("status")
    private String status; // Will be ACTIVE or INACTIVE from API
    
    private Boolean isActive; // For internal use

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getUsageLimit() {
        // Return maxUsage from API if usageLimit is null
        return usageLimit != null ? usageLimit : maxUsage;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(Integer maxUsage) {
        this.maxUsage = maxUsage;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Double getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(Double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public Double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(Double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public String getEndDate() {
        // Return expiryDate from API if endDate is null
        return endDate != null ? endDate : expiryDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        // Convert status to isActive
        this.isActive = "ACTIVE".equalsIgnoreCase(status);
    }

    public Boolean getIsActive() {
        if (isActive == null && status != null) {
            return "ACTIVE".equalsIgnoreCase(status);
        }
        return isActive != null ? isActive : false;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    // Calculate actual discount amount based on type
    public double calculateDiscount(double orderTotal) {
        if (!getIsActive() || discountValue == null) {
            return 0;
        }
        
        // Check if order meets minimum value
        if (minOrderValue != null && orderTotal < minOrderValue) {
            return 0;
        }
        
        double discount = 0;
        
        if ("FIXED_AMOUNT".equals(discountType)) {
            discount = discountValue;
        } else if ("PERCENTAGE".equals(discountType)) {
            discount = orderTotal * (discountValue / 100);
            // Apply max discount cap if set
            if (maxDiscountAmount != null && discount > maxDiscountAmount) {
                discount = maxDiscountAmount;
            }
        }
        
        return discount;
    }
}
