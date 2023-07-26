package com.funeat.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funeat.auth.dto.KakaoTokenDto;
import com.funeat.auth.dto.KakaoUserInfoDto;
import com.funeat.auth.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("!test")
public class KakaoPlatformUserProvider implements PlatformUserProvider {

    private static final String AUTHORIZATION_BASE_URL = "https://kauth.kakao.com";
    private static final String RESOURCE_BASE_URL = "https://kapi.kakao.com";
    private static final String ACCESS_TOKEN_URI = "/oauth/token";
    private static final String USER_INFO_URI = "/v2/user/me";
    private static final String AUTHORIZATION_CODE = "authorization_code";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String kakaoRestApiKey;
    private final String redirectUri;

    public KakaoPlatformUserProvider(final RestTemplateBuilder restTemplateBuilder,
                                     final ObjectMapper objectMapper,
                                     @Value("${kakao.rest-api-key}") final String kakaoRestApiKey,
                                     @Value("${kakao.redirect-uri}") final String redirectUri) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.redirectUri = redirectUri;
    }

    public UserInfoDto getPlatformUser(final String code) {
        final KakaoTokenDto accessTokenDto = findAccessToken(code);
        final KakaoUserInfoDto kakaoUserInfoDto = findKakaoUserInfo(accessTokenDto.getAccessToken());
        return UserInfoDto.from(kakaoUserInfoDto);
    }

    private KakaoTokenDto findAccessToken(final String code) {
        final ResponseEntity<String> response = requestAccessToken(code);
        validateResponse(response);
        return convertJsonToKakaoTokenDto(response.getBody());
    }

    private ResponseEntity<String> requestAccessToken(final String code) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", AUTHORIZATION_CODE);
        body.add("client_id", kakaoRestApiKey);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        final ResponseEntity<String> response = restTemplate.postForEntity(AUTHORIZATION_BASE_URL + ACCESS_TOKEN_URI,
                request, String.class);

        return response;
    }

    private void validateResponse(final ResponseEntity<String> response) {
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException();
        }
    }

    private KakaoTokenDto convertJsonToKakaoTokenDto(final String responseBody) {
        try {
            return objectMapper.readValue(responseBody, KakaoTokenDto.class);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    private KakaoUserInfoDto findKakaoUserInfo(final String accessToken) {
        final ResponseEntity<String> response = requestKakaoUserInfo(accessToken);
        validateResponse(response);
        return convertJsonToKakaoUserDto(response.getBody());
    }

    private ResponseEntity<String> requestKakaoUserInfo(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(RESOURCE_BASE_URL + USER_INFO_URI, request,
                String.class);
        return response;
    }

    private KakaoUserInfoDto convertJsonToKakaoUserDto(final String responseBody) {
        try {
            return objectMapper.readValue(responseBody, KakaoUserInfoDto.class);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }
}
