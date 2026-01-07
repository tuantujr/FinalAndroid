package com.example.ute.models.request;

import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequest {
    @SerializedName("email")
    private String email;
    
    @SerializedName("otp")
    private String otp;
    
    @SerializedName("newPassword")
    private String newPassword;
    
    // Constructor
    public ForgotPasswordRequest() {}
    
    public ForgotPasswordRequest(String email) {
        this.email = email;
    }
    
    public ForgotPasswordRequest(String email, String otp, String newPassword) {
        this.email = email;
        this.otp = otp;
        this.newPassword = newPassword;
    }
    
    // Getters and Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getOtp() {
        return otp;
    }
    
    public void setOtp(String otp) {
        this.otp = otp;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
