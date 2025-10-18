package com.smart.pantry.backend.application.controller;

import com.smart.pantry.backend.application.dto.GcsUrlResponse;
import com.smart.pantry.backend.application.service.GcsSignedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/storage")
public class GcsController {

    @Autowired
    private GcsSignedUrlService gcsSignedUrlService;

    @GetMapping("/generate-upload-url")
    public ResponseEntity<GcsUrlResponse> generateUploadUrl() {

        // You might want to sanitize the fileName or generate a unique one

        GcsUrlResponse urlResponse = gcsSignedUrlService.generateV4PutSignedUrl();

        return new ResponseEntity<>(urlResponse, HttpStatus.CREATED);
    }
}