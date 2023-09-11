package com.funeat.common.dto;

public class S3UrlResponse {

    private final String preSignedUrl;

    public S3UrlResponse(final String preSignedUrl) {
        this.preSignedUrl = preSignedUrl;
    }

    public String getPreSignedUrl() {
        return preSignedUrl;
    }
}
