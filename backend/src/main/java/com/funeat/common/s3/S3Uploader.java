package com.funeat.common.s3;

import static com.funeat.exception.CommonErrorCode.IMAGE_EXTENSION_ERROR_CODE;
import static com.funeat.exception.CommonErrorCode.UNKNOWN_SERVER_ERROR_CODE;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.funeat.common.ImageUploader;
import com.funeat.common.exception.CommonException.NotAllowedFileExtensionException;
import com.funeat.common.exception.CommonException.S3UploadFailException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("!test")
public class S3Uploader implements ImageUploader {

    private static final List<String> INCLUDE_EXTENSIONS = List.of("image/jpeg", "image/png", "image/webp");

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    @Value("${cloud.aws.s3.cloudfrontPath}")
    private String cloudfrontPath;

    private final AmazonS3 amazonS3;

    public S3Uploader(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String upload(final MultipartFile image) {
        validateExtension(image);
        final String randomImageName = getRandomImageName(image);
        final ObjectMetadata metadata = getMetadata(image);
        try {
            final String key = folder + randomImageName;
            amazonS3.putObject(getPutObjectRequest(image, key, metadata));

            return getCloudfrontImagePath(randomImageName);
        } catch (IOException e) {
            throw new S3UploadFailException(UNKNOWN_SERVER_ERROR_CODE);
        }
    }

    private void validateExtension(final MultipartFile image) {
        final String contentType = image.getContentType();
        if (!INCLUDE_EXTENSIONS.contains(contentType)) {
            throw new NotAllowedFileExtensionException(IMAGE_EXTENSION_ERROR_CODE, contentType);
        }
    }

    private String getRandomImageName(final MultipartFile image) {
        return UUID.randomUUID() + image.getOriginalFilename();
    }

    private ObjectMetadata getMetadata(final MultipartFile image) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());
        return metadata;
    }

    private PutObjectRequest getPutObjectRequest(final MultipartFile image, final String key,
                                                 final ObjectMetadata metadata) throws IOException {
        return new PutObjectRequest(bucket, key, image.getInputStream(), metadata);
    }

    private String getCloudfrontImagePath(final String imageName) {
        return cloudfrontPath + imageName;
    }
}
