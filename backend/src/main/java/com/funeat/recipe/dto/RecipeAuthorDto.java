package com.funeat.recipe.dto;

import com.funeat.member.domain.Member;

public class RecipeAuthorDto {

    private final String profileImage;
    private final String nickName;

    private RecipeAuthorDto(final String profileImage, final String nickName) {
        this.profileImage = profileImage;
        this.nickName = nickName;
    }

    public static RecipeAuthorDto toDto(final Member member) {
        return new RecipeAuthorDto(member.getProfileImage(), member.getNickname());
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getNickName() {
        return nickName;
    }
}
