package com.funeat.recipe.dto;

import com.funeat.member.domain.Member;

public class RecipeAuthorDto {

    private final String profileImage;
    private final String nickname;

    private RecipeAuthorDto(final String profileImage, final String nickname) {
        this.profileImage = profileImage;
        this.nickname = nickname;
    }

    public static RecipeAuthorDto toDto(final Member member) {
        return new RecipeAuthorDto(member.getProfileImage(), member.getNickname());
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getNickname() {
        return nickname;
    }
}
