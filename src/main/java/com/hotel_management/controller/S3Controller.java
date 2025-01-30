package com.hotel_management.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.hotel_management.service.PropertyService;
import com.hotel_management.service.S3Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;
    private final PropertyService propertyService;

    public S3Controller(S3Service s3Service, PropertyService propertyService) {
        this.s3Service = s3Service;
        this.propertyService = propertyService;
    }

    // Upload file API
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("propertyId") long propertyId
    ) {
        // Upload file to S3 and get the file URL
        String fileUrl = s3Service.uploadFile(file);

        // Save the file URL to the database
        propertyService.saveImageUrl(propertyId, fileUrl);

        return ResponseEntity.ok("File uploaded successfully. URL: " + fileUrl);
    }

    // Download file API
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable String fileName
        ) throws IOException {

        S3Object s3Object = s3Service.downloadFile(fileName);
        InputStream inputStream = s3Object.getObjectContent();
        byte[] bytes = inputStream.readAllBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(bytes);
    }

    // Delete file API
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return ResponseEntity.ok(s3Service.deleteFile(fileName));
    }

    // List files API
    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        return ResponseEntity.ok(s3Service.listFiles());
    }
}
