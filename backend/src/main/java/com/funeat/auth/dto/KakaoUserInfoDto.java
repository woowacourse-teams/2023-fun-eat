package com.funeat.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoUserInfoDto {

    private final Long id;
    private final KakaoAccount kakaoAccount;

    @JsonCreator
    public KakaoUserInfoDto(@JsonProperty("id") final Long id,
                            @JsonProperty("kakao_account") final KakaoAccount kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }

    public Long getId() {
        return id;
    }

    public KakaoAccount getKakaoAccount() {
        return kakaoAccount;
    }

    public static class KakaoAccount {

        private final KakaoProfile profile;

        @JsonCreator
        public KakaoAccount(@JsonProperty("profile") final KakaoProfile profile) {
            this.profile = profile;
        }

        public KakaoProfile getProfile() {
            return profile;
        }
    }

    public static class KakaoProfile {

        private final String nickname;
        private final String profileImageUrl;

        @JsonCreator
        public KakaoProfile(
                @JsonProperty("nickname") final String nickname,
                @JsonProperty("profile_image_url") final String profileImageUrl) {
            this.nickname = nickname;
            this.profileImageUrl = profileImageUrl;
        }

        public String getNickname() {
            return nickname;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }
    }
}
