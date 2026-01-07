package com.example.ute.models.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class VoucherValidationResponse {
    
    @SerializedName("success")
    private Boolean success;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("data")
    private VoucherValidationData data;

    public VoucherValidationResponse() {
    }

    public VoucherValidationResponse(Boolean success, String message, VoucherValidationData data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean isSuccess() {
        return success != null && success;
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

    public VoucherValidationData getData() {
        return data;
    }

    public void setData(VoucherValidationData data) {
        this.data = data;
    }

    /**
     * Inner class representing the actual validation data from backend
     */
    public static class VoucherValidationData {
        @SerializedName("valid")
        private Boolean valid;
        
        @SerializedName("message")
        private String message;
        
        @SerializedName("voucher")
        private VoucherInfo voucher;
        
        @SerializedName("discountAmount")
        private Double discountAmount;
        
        @SerializedName("finalAmount")
        private Double finalAmount;

        public VoucherValidationData() {
        }

        public Boolean isValid() {
            return valid != null && valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public VoucherInfo getVoucher() {
            return voucher;
        }

        public void setVoucher(VoucherInfo voucher) {
            this.voucher = voucher;
        }

        public Double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Double discountAmount) {
            this.discountAmount = discountAmount;
        }

        public Double getFinalAmount() {
            return finalAmount;
        }

        public void setFinalAmount(Double finalAmount) {
            this.finalAmount = finalAmount;
        }

        /**
         * Nested class for voucher information
         */
        public static class VoucherInfo {
            @SerializedName("code")
            private String code;
            
            @SerializedName("discountType")
            private String discountType;
            
            @SerializedName("discountValue")
            private Double discountValue;
            
            @SerializedName("minOrderValue")
            private Double minOrderValue;

            public VoucherInfo() {
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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

            public Double getMinOrderValue() {
                return minOrderValue;
            }

            public void setMinOrderValue(Double minOrderValue) {
                this.minOrderValue = minOrderValue;
            }
        }
    }
}
