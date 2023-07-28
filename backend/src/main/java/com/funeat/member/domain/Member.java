package com.funeat.member.domain;

import com.funeat.member.domain.bookmark.ProductBookmark;
import com.funeat.member.domain.bookmark.RecipeBookmark;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.member.domain.favorite.ReviewFavorite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String profileImage;

    private String platformId;

    @OneToMany(mappedBy = "member")
    private List<ReviewFavorite> reviewFavorites = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<RecipeFavorite> recipeFavorites;

    @OneToMany(mappedBy = "member")
    private List<ProductBookmark> productBookmarks;

    @OneToMany(mappedBy = "member")
    private List<RecipeBookmark> recipeBookmarks;

    protected Member() {
    }

    public Member(final String nickname, final String profileImage, final String platformId) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.platformId = platformId;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPlatformId() {
        return platformId;
    }

    public List<ReviewFavorite> getReviewFavorites() {
        return reviewFavorites;
    }

    public List<RecipeFavorite> getRecipeFavorites() {
        return recipeFavorites;
    }

    public List<ProductBookmark> getProductBookmarks() {
        return productBookmarks;
    }

    public List<RecipeBookmark> getRecipeBookmarks() {
        return recipeBookmarks;
    }
}
