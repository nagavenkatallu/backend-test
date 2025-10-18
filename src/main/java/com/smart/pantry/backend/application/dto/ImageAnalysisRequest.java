package com.smart.pantry.backend.application.dto;

import jakarta.validation.constraints.NotBlank;

public class ImageAnalysisRequest {

    @NotBlank(message = "fileName cannot be blank")
    private String fileName;

    // Standard getters and setters

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
