package com.funeat.auth.dto;

public class TokenResponse {

    private String accessToken;

    public TokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
