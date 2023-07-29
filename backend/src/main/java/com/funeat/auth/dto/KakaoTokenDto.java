package com.funeat.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoTokenDto {

    private final String accessToken;
    private final String tokenType;
    private final String refreshToken;
    private final String expiresIn;
    private final String scope;
    private final String refreshTokenExpiresIn;

    @JsonCreator
    public KakaoTokenDto(@JsonProperty("access_token") final String accessToken,
                         @JsonProperty("token_type") final String tokenType,
                         @JsonProperty("refresh_token") final String refreshToken,
                         @JsonProperty("expires_in") final String expiresIn,
                         @JsonProperty("scope") final String scope,
                         @JsonProperty("refresh_token_expires_in") final String refreshTokenExpiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public String getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }
}
