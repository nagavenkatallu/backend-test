package com.smart.pantry.backend.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    // Getters and Setters are required for Jackson to deserialize the JSON body.
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
