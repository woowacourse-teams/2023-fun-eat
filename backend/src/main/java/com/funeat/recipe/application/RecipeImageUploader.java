package com.funeat.recipe.application;

import com.funeat.review.application.ImageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("recipeImageUploader")
@Profile("!test")
public class RecipeImageUploader implements ImageService {

    @Value("${recipe.image.path}")
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
