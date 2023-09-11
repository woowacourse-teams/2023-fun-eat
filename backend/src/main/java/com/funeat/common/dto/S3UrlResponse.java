package com.funeat.common.dto;

public class S3UrlResponse {

    private final String preSingedUrl;

    public S3UrlResponse(final String preSingedUrl) {
        this.preSingedUrl = preSingedUrl;
    }

    public String getPreSingedUrl() {
        return preSingedUrl;
    }
}
