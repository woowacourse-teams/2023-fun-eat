package com.funeat.common.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3UploadUrlGenerator {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.folder}")
    private String folder;

    @Value("${cloud.aws.s3.expiration.time}")
    private long expirationTime;

    private final AmazonS3 amazonS3;

    public S3UploadUrlGenerator(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String getPreSignedUrl(final String fileName) {
        final GeneratePresignedUrlRequest generatePresignedUrlRequest = getPreSignedUrlRequest(bucket, fileName);

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private GeneratePresignedUrlRequest getPreSignedUrlRequest(final String bucket, final String fileName) {
        final Date madeExpirationTime = getExpirationTime();
        final GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucket,
                folder + fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(madeExpirationTime);

        urlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());

        return urlRequest;
    }

    private Date getExpirationTime() {
        final Date madeExpirationTime = new Date();
        madeExpirationTime.setTime(expirationTime);
        return madeExpirationTime;
    }
}
