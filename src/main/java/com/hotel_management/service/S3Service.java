package com.hotel_management.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;
    private final TransferManager transferManager; // Reuse TransferManager

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
        this.transferManager = TransferManagerBuilder.standard()
                .withS3Client(amazonS3)
                .build();
    }

    // Upload a file to S3
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            // Upload the file using TransferManager
            Upload upload = transferManager.upload(bucketName, fileName, file.getInputStream(), new ObjectMetadata());
            upload.waitForCompletion(); // Wait for the upload to finish

            return getFileUrl(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            throw new RuntimeException("Upload was interrupted", e);
        }
    }

    // Get the file URL
    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    // Download a file from S3
    public S3Object downloadFile(String fileName) {
        return amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
    }

    // Delete a file from S3
    public String deleteFile(String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
        return "File deleted successfully: " + fileName;
    }

    // List all files in the bucket
    public List<String> listFiles() {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        return objectListing.getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

}