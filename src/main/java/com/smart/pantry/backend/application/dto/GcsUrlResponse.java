package com.smart.pantry.backend.application.dto;

public class GcsUrlResponse {
    private String url;
    private String filename;

    public GcsUrlResponse(String url, String filename) {
        this.url = url;
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
