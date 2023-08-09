package com.funeat.recipe.dto;

public class RecipeAuthorDto {

    private final String profileImage;
    private final String nickName;

    public RecipeAuthorDto(final String profileImage, final String nickName) {
        this.profileImage = profileImage;
        this.nickName = nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getNickName() {
        return nickName;
    }
}
