package dev.sule.ecommerce.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileStorageUtil {

    private static final String UPLOAD_DIR = "uploads";

    // Ensure the upload directory exists
    public static void initializeDirectory() {
        Path path = Paths.get(UPLOAD_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory", e);
            }
        }
    }

    // Save an image to the directory and return the file path
    public static String saveImage(MultipartFile file) {
        try {
            // Generate a unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Get the full path
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            // Save the file
            Files.write(filePath, file.getBytes());

            return fileName; // Return the saved file name
        } catch (IOException e) {
            throw new RuntimeException("Could not save file: " + file.getOriginalFilename(), e);
        }
    }
}

