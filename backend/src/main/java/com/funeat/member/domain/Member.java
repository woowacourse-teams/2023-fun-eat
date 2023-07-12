package com.funeat.member.domain;

import com.funeat.member.domain.bookmark.ProductBookmark;
import com.funeat.member.domain.bookmark.RecipeBookmark;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.member.domain.favorite.ReviewFavorite;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    @OneToMany(mappedBy = "member")
    private List<ReviewFavorite> reviewFavorites;

    @OneToMany(mappedBy = "member")
    private List<RecipeFavorite> recipeFavorites;

    @OneToMany(mappedBy = "member")
    private List<ProductBookmark> productBookmarks;

    @OneToMany(mappedBy = "member")
    private List<RecipeBookmark> recipeBookmarks;
}
