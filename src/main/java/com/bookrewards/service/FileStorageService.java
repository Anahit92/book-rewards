package com.bookrewards.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    
    private final Path uploadDir = Paths.get("books");
    
    public FileStorageService() {
        // Create the upload directory if it doesn't exist
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }
    
    public String storeFile(MultipartFile file) throws IOException {
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        String filename = UUID.randomUUID().toString() + fileExtension;
        Path filePath = uploadDir.resolve(filename);
        
        // Save the file
        Files.copy(file.getInputStream(), filePath);
        
        return filename;
    }
    
    public Path getFilePath(String filename) {
        return uploadDir.resolve(filename);
    }
    
    public boolean fileExists(String filename) {
        return Files.exists(uploadDir.resolve(filename));
    }
    
    public void deleteFile(String filename) throws IOException {
        Path filePath = uploadDir.resolve(filename);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
} 