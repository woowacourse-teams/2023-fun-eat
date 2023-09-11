package com.funeat.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3Uploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    private final AmazonS3 amazonS3;

    public S3Uploader(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
}
