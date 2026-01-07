package com.example.ute.models.request;

import com.google.gson.annotations.SerializedName;

/**
 * Request body for change password endpoint
 * POST /api/v1/user/password
 */
public class ChangePasswordRequest {
    @SerializedName("oldPassword")
    private String oldPassword;
    
    @SerializedName("newPassword")
    private String newPassword;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
