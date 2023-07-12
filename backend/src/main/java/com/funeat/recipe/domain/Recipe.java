package com.funeat.recipe.domain;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.bookmark.RecipeBookmark;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.product.domain.ProductRecipe;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "recipe")
    private List<ProductRecipe> productRecipes;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeFavorite> recipeFavorites;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeBookmark> recipeBookmarks;
}
