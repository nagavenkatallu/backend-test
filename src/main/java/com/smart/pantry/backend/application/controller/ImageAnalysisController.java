package com.smart.pantry.backend.application.controller;

import com.smart.pantry.backend.application.dto.AIPantryItem;
import com.smart.pantry.backend.application.dto.ImageAnalysisRequest;
import com.smart.pantry.backend.application.service.ImageAnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/vertexai")
public class ImageAnalysisController {

    private final ImageAnalysisService imageAnalysisService;

    public ImageAnalysisController(ImageAnalysisService imageAnalysisService) {
        this.imageAnalysisService = imageAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeImage(@Valid @RequestBody ImageAnalysisRequest request){
        try {
            List<AIPantryItem> result = imageAnalysisService.analyzeImage(request.getFileName());
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            // This specifically catches GCS errors (e.g., file not found)
            return ResponseEntity.status(500).body("Error accessing file from Cloud Storage: " + e.getMessage());
        } catch (RuntimeException e) {
            // This catches errors from the Vertex AI service call
            return ResponseEntity.status(500).body("Error during image analysis: " + e.getMessage());
        } catch (Exception e) {
            // A general catch-all for any other unexpected errors
            return ResponseEntity.status(500).body("An unexpected server error occurred.");
        }
    }
}
