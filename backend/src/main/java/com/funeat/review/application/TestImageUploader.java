package com.funeat.review.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("test")
public class TestImageUploader implements ImageService {

    @Override
    public void upload(final MultipartFile image) {
        // 실제로 IO 작업을 수행하는 대신, 임시 디렉토리로 복사하도록 수정
        try {
            final String temporaryPath = String.valueOf(System.currentTimeMillis());
            final Path tempDirectoryPath = Files.createTempDirectory(temporaryPath);
            final Path filePath = tempDirectoryPath.resolve(image.getOriginalFilename());
            Files.copy(image.getInputStream(), filePath);

            deleteDirectory(tempDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteDirectory(Path directory) throws IOException {
        // 디렉토리 내부 파일 및 디렉토리 삭제
        try (Stream<Path> pathStream = Files.walk(directory)) {
            pathStream
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        Files.deleteIfExists(directory);
    }
}
