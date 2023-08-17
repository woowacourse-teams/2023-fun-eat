package com.funeat.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class MemberRequest {

    @NotBlank(message = "닉네임을 확인해주세요")
    private final String nickname;

    public MemberRequest(@JsonProperty("nickname") final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
