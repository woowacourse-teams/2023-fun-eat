package com.funeat.common.controller;

import com.funeat.common.dto.S3UrlRequest;
import com.funeat.common.dto.S3UrlResponse;
import com.funeat.common.s3.S3UploadUrlGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreSingedApiController implements PreSingedController {

    private final S3UploadUrlGenerator s3UploadUrlGenerator;

    public PreSingedApiController(final S3UploadUrlGenerator s3UploadUrlGenerator) {
        this.s3UploadUrlGenerator = s3UploadUrlGenerator;
    }

    @PostMapping("/api/s3/presigned")
    public ResponseEntity<S3UrlResponse> getPreSingedUrl(@RequestBody final S3UrlRequest request) {
        final S3UrlResponse preSignedUrl = s3UploadUrlGenerator.getPreSignedUrl(request.getFileName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(preSignedUrl);
    }
}
