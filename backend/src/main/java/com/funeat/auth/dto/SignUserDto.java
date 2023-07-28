package com.funeat.auth.dto;

import com.funeat.member.domain.Member;

public class SignUserDto {

    private final boolean isSignIn;
    private final Member member;

    public SignUserDto(final boolean isSignIn, final Member member) {
        this.isSignIn = isSignIn;
        this.member = member;
    }

    public static SignUserDto of(final boolean isSignIn, final Member member) {
        return new SignUserDto(isSignIn, member);
    }

    public boolean isSignIn() {
        return isSignIn;
    }

    public Member getMember() {
        return member;
    }
}
