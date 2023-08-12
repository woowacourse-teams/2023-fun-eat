package com.funeat.auth.dto;

import com.funeat.member.domain.Member;

public class SignUserDto {

    private final boolean isSignUp;
    private final Member member;

    public SignUserDto(final boolean isSignUp, final Member member) {
        this.isSignUp = isSignUp;
        this.member = member;
    }

    public static SignUserDto of(final boolean isSignUp, final Member member) {
        return new SignUserDto(isSignUp, member);
    }

    public boolean isSignUp() {
        return isSignUp;
    }

    public Member getMember() {
        return member;
    }
}
