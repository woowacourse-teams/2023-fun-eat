package com.funeat.recipe.dto;

import com.funeat.member.domain.Member;

public class RecipeCommentMemberResponse {

    private final String nickname;
    private final String profileImage;

    private RecipeCommentMemberResponse(final String nickname, final String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static RecipeCommentMemberResponse toResponse(final Member member) {
        return new RecipeCommentMemberResponse(member.getNickname(), member.getProfileImage());
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
