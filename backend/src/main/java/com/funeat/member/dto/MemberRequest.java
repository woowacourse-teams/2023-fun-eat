package com.funeat.member.dto;

public class MemberRequest {

    private final String nickname;
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
