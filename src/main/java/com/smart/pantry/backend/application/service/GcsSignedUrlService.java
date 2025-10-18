package com.smart.pantry.backend.application.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import com.smart.pantry.backend.application.dto.GcsUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class GcsSignedUrlService {

    @Autowired
    private Storage storage;

    @Value("${gcs.bucket.name}")
    private String bucketName;

    /**
     * Generates a v4 signed URL for uploading a file.
     *
     * @param objectName The name of the file to be uploaded in the GCS bucket.
     * @return The signed URL for the PUT request.
     */
    public GcsUrlResponse generateV4PutSignedUrl() {
        // Define the blob info
        String objectName = UUID.randomUUID().toString() + ".jpg";
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                // The content type is crucial for the client to set in their request header
                .setContentType("image/jpeg")
                .build();

        // Generate the signed URL
        try {
            URL url = storage.signUrl(
                    blobInfo,
                    15, // URL expiration time
                    TimeUnit.MINUTES, // Time unit
                    Storage.SignUrlOption.httpMethod(HttpMethod.PUT), // Allow PUT requests
                    Storage.SignUrlOption.withV4Signature() // Use V4 signing
            );
            return new GcsUrlResponse(url.toString(),objectName);
//            return url.toString();
        } catch (Exception e) {
            // Handle exceptions, e.g., logging
            throw new RuntimeException("Failed to generate signed URL: " + e.getMessage(), e);
        }
    }
}