package com.funeat.recipe.dto;

import java.util.List;

public class RecipeDetailResponse {

    private final Long id;
    private final List<String> images;
    private final String title;
    private final String content;
    private final RecipeAuthorDto author;
    private final List<ProductRecipeDto> products;
    private final Long totalPrice;
    private final Long favoriteCount;
    private final Boolean favorite;

    public RecipeDetailResponse(final Long id, final List<String> images, final String title, final String content,
                                final RecipeAuthorDto author,
                                final List<ProductRecipeDto> products, final Long totalPrice, final Long favoriteCount,
                                final Boolean favorite) {
        this.id = id;
        this.images = images;
        this.title = title;
        this.content = content;
        this.author = author;
        this.products = products;
        this.totalPrice = totalPrice;
        this.favoriteCount = favoriteCount;
        this.favorite = favorite;
    }

    public Long getId() {
        return id;
    }

    public List<String> getImages() {
        return images;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public RecipeAuthorDto getAuthor() {
        return author;
    }

    public List<ProductRecipeDto> getProducts() {
        return products;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public Boolean getFavorite() {
        return favorite;
    }
}
