package com.funeat.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funeat.auth.dto.KakaoTokenDto;
import com.funeat.auth.dto.KakaoUserInfoDto;
import com.funeat.auth.dto.UserInfoDto;
import java.util.StringJoiner;
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
    private static final String OAUTH_URI = "/oauth/authorize";
    private static final String ACCESS_TOKEN_URI = "/oauth/token";
    private static final String USER_INFO_URI = "/v2/user/me";
    private static final String LOGOUT_URI = "/v1/user/logout";
    private static final String AUTHORIZATION_CODE = "authorization_code";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String kakaoRestApiKey;
    private final String redirectUri;
    private final String kakaoAdminKey;

    public KakaoPlatformUserProvider(final RestTemplateBuilder restTemplateBuilder,
                                     final ObjectMapper objectMapper,
                                     @Value("${kakao.rest-api-key}") final String kakaoRestApiKey,
                                     @Value("${kakao.redirect-uri}") final String redirectUri,
                                     @Value("${kakao.admin-key") final String kakaoAdminKey) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.redirectUri = redirectUri;
        this.kakaoAdminKey = kakaoAdminKey;
    }

    @Override
    public UserInfoDto getPlatformUser(final String code) {
        final KakaoTokenDto accessTokenDto = findAccessToken(code);
        final KakaoUserInfoDto kakaoUserInfoDto = findKakaoUserInfo(accessTokenDto.getAccessToken());
        return UserInfoDto.from(kakaoUserInfoDto);
    }

    private KakaoTokenDto findAccessToken(final String code) {
        final ResponseEntity<String> response = requestAccessToken(code);
        validateResponse(response, HttpStatus.OK);
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

    private void validateResponse(final ResponseEntity<String> response, final HttpStatus status) {
        if (response.getStatusCode() != status) {
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
        validateResponse(response, HttpStatus.OK);
        return convertJsonToKakaoUserDto(response.getBody());
    }

    private ResponseEntity<String> requestKakaoUserInfo(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        return restTemplate.postForEntity(RESOURCE_BASE_URL + USER_INFO_URI, request, String.class);
    }

    private KakaoUserInfoDto convertJsonToKakaoUserDto(final String responseBody) {
        try {
            return objectMapper.readValue(responseBody, KakaoUserInfoDto.class);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getRedirectURI() {
        final StringJoiner joiner = new StringJoiner("&");
        joiner.add("response_type=code");
        joiner.add("client_id=" + kakaoRestApiKey);
        joiner.add("redirect_uri=" + redirectUri);

        return AUTHORIZATION_BASE_URL + OAUTH_URI + "?" + joiner;
    }

    @Override
    public void logout(final String platformId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + kakaoAdminKey);

        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("target_id_type", "user_id");
        body.add("target_id", platformId);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(RESOURCE_BASE_URL + LOGOUT_URI, request, String.class);
    }
}
