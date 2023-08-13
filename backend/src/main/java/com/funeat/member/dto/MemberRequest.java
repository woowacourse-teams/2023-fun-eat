package com.funeat.member.dto;

import javax.validation.constraints.NotBlank;

public class MemberRequest {

    @NotBlank(message = "닉네임을 확인해주세요")
    private final String nickname;

    @NotBlank(message = "프로필 이미지를 확인해주세요")
    private final String profileImage;

    public MemberRequest(final String nickname, final String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
