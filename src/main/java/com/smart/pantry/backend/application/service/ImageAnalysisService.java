package com.smart.pantry.backend.application.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Blob;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

/**
 * Service to handle image analysis using Google Cloud Storage and Vertex AI.
 */
@Service
public class ImageAnalysisService {
    // Define a clear, reusable prompt for your pantry analysis.
    private static final String PROMPT = "You are an intelligent data extraction agent. Analyze the following image of a grocery receipt. Your task is to extract only the food and beverage items. Ignore all other information like store name, address, tax, totals, or non-food items. For each food item, identify its name and quantity (if specified, otherwise assume 1). Your entire response must be a single, valid JSON array of objects. Each object must have two keys: a string 'itemName' and a number 'quantity' and a string 'unit'. For example: [{'itemName': 'Organic Milk', 'quantity': 1, 'unit': Gallon }, {'itemName': 'Rice', 'quantity': 20, 'unit':lbs}]";

    private final Storage storage;
    private final String bucketName;
    private final String projectId;
    private final String location;
    private final String modelName;

    /**
     * Constructor for dependency injection.
     * Spring Cloud GCP automatically configures the Storage bean.
     * Other values are injected from application.properties.
     */
    public ImageAnalysisService(
            Storage storage,
            @Value("${gcs.bucket.name}") String bucketName,
            @Value("${gcp.project-id}") String projectId,
            @Value("${gcp.location}") String location,
            @Value("${vertex.ai.model-name}") String modelName) {
        this.storage = storage;
        this.bucketName = bucketName;
        this.projectId = projectId;
        this.location = location;
        this.modelName = modelName;
    }

    /**
     * Analyzes an image from GCS using a predefined prompt with Vertex AI.
     *
     * @param fileName The name of the file in the GCS bucket.
     * @return The text response from the Vertex AI model.
     * @throws IOException if there is an issue reading the file from GCS.
     */
    public String analyzeImage(String fileName) throws IOException {

        BlobId blobId = BlobId.of(bucketName, fileName);
        byte[] imageBytes = storage.readAllBytes(blobId);

        // 2. Initialize the Vertex AI client within a try-with-resources block
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {

            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
            System.out.println("Created model:"+model.getModelName());
            // 3. Construct the prompt with the image and text
//            Part imagePart = Part.newBuilder()
//                    .setMimeType("image/jpeg") // IMPORTANT: Change to "image/png" or other if needed
//                    .setData(ByteString.copyFrom(imageBytes))
//                    .build();

            Part imagePart = Part.newBuilder()
                    .setInlineData(
                            Blob.newBuilder()
                                    .setMimeType("image/jpeg") // Set MIME type on the Blob
                                    .setData(ByteString.copyFrom(imageBytes)) // Set data on the Blob
                                    .build()
                    ).build();
            System.out.println("Created image part");
            Part textPart = Part.newBuilder()
                    .setText(PROMPT)
                    .build();
            System.out.println("Created text part");
            // 4. Send the request to the model

//            GenerateContentResponse response = model.generateContent(Collections.singletonList(
//                    com.google.cloud.vertexai.api.Content.newBuilder()
//                            .addParts(imagePart) // The image data
//                            .addParts(textPart)  // The instructional prompt
//                            .build()
//            ));

            GenerateContentResponse response = model.generateContent(Collections.singletonList(
                    com.google.cloud.vertexai.api.Content.newBuilder()
                            // ## FIX: Add the role for the content ##
                            .setRole("user")
                            .addParts(imagePart)
                            .addParts(textPart)
                            .build()
            ));

            // 5. Extract and return the text response
            String analysisResult = ResponseHandler.getText(response);

            return analysisResult;
        } catch (Exception e) {
            // Re-throw as a runtime exception to be handled by the controller advice/exception handler
            e.printStackTrace();
            throw new RuntimeException("Failed to analyze image with Vertex AI.", e);
        }
    }
}

