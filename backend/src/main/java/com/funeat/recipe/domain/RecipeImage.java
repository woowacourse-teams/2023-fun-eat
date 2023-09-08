package com.funeat.recipe.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecipeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    protected RecipeImage() {
    }

    public RecipeImage(final String image, final Recipe recipe) {
        this.image = image;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
