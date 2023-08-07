package com.funeat.review.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("!test")
public class ImageUploader implements ImageService {

    @Value("${review.image.path}")
    private String imagePath;

    @Override
    public void upload(final MultipartFile image) {
        final String originalImageName = image.getOriginalFilename();
        final Path path = Paths.get(imagePath + originalImageName);
        try {
            Files.write(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
