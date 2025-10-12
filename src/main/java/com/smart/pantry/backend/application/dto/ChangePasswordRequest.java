package com.smart.pantry.backend.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    @Size(min = 8,message = "New password must be at least 8 characters long")
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
