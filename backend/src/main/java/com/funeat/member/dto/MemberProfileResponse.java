package com.funeat.member.dto;

import com.funeat.member.domain.Member;

public class MemberProfileResponse {

    private final String nickname;
    private final String profileImage;

    public MemberProfileResponse(final String nickname, final String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static MemberProfileResponse toResponse(final Member member) {
        return new MemberProfileResponse(member.getNickname(), member.getProfileImage());
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
