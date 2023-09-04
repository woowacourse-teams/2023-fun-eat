package com.funeat.member.dto;

import javax.validation.constraints.NotBlank;

public class MemberRequest {

    @NotBlank(message = "닉네임을 확인해주세요")
    private final String nickname;

    private final String image;

    public MemberRequest(final String nickname, final String image) {
        this.nickname = nickname;
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImage() {
        return image;
    }
}
