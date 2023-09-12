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
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("!test")
public class S3Uploader implements ImageUploader {

    public static final String JPEG = "image/jpeg";
    public static final String PNG = "image/png";

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

            return getCloudfrontImagePath(key);
        } catch (IOException e) {
            throw new S3UploadFailException(UNKNOWN_SERVER_ERROR_CODE);
        }
    }

    private void validateExtension(final MultipartFile image) {
        final String contentType = image.getContentType();
        if (!contentType.equals(JPEG) && !contentType.equals(PNG)) {
            throw new NotAllowedFileExtensionException(IMAGE_EXTENSION_ERROR_CODE, contentType);
        }
    }

    private String getRandomImageName(final MultipartFile image) {
        final String randomImageName = UUID.randomUUID() + image.getOriginalFilename();
        return randomImageName.substring(0, randomImageName.lastIndexOf("."));
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

    private String getCloudfrontImagePath(final String key) {
        final String s3Url = amazonS3.getUrl(bucket, key).toString();
        return cloudfrontPath + s3Url.substring(s3Url.lastIndexOf("/"));
    }
}
