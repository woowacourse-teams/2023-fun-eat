package com.funeat.auth.dto;

import com.funeat.member.domain.Member;

public class UserInfoDto {

    private final Long id;
    private final String nickname;
    private final String profileImageUrl;

    public UserInfoDto(final Long id, final String nickname, final String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static UserInfoDto from(final KakaoUserInfoDto kakaoUserInfoDto) {
        return new UserInfoDto(
                kakaoUserInfoDto.getId(),
                kakaoUserInfoDto.getKakaoAccount().getProfile().getNickname(),
                kakaoUserInfoDto.getKakaoAccount().getProfile().getProfileImageUrl()
        );
    }

    public Member toMember() {
        return new Member(this.nickname, this.profileImageUrl, this.id.toString());
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
