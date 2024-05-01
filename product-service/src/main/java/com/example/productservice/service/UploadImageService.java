package com.example.productservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UploadImageService {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    public String saveImage(MultipartFile file) {
        try {
            if (!isImage(file.getOriginalFilename())) {
                throw new IllegalArgumentException("Uploaded file is not an image");
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + file.getOriginalFilename());
            Files.write(path, bytes);
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image");
        }
    }
    private boolean isImage(String filename) {
        List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
        String fileExtension = filename.substring(filename.lastIndexOf('.')).toLowerCase();
        return allowedExtensions.contains(fileExtension);
    }


}
