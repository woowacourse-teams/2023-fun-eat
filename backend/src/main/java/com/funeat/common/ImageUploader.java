package com.funeat.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("!test")
public class ImageUploader implements ImageService {

    @Value("${image.path}")
    private String imagePath;

    @Override
    public void upload(final MultipartFile image, final String newFileName) {
        final Path path = Paths.get(imagePath + newFileName);
        try {
            Files.write(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRandomImageName(final MultipartFile image) {
        return UUID.randomUUID() + image.getOriginalFilename();
    }
}
