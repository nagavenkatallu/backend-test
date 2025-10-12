package com.smart.pantry.backend.application.dto;

import jakarta.validation.constraints.Size;

public class UserUpdateRequest {

    @Size(max = 50)
    private String firstName;

    @Size
    private String lastName;

    @Size
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
