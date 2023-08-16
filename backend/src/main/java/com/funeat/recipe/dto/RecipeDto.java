package com.funeat.recipe.dto;

import com.funeat.product.domain.Product;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeDto {

    private final Long id;
    private final String image;
    private final String title;
    private final RecipeAuthorDto author;
    private final List<ProductRecipeDto> products;
    private final Long favoriteCount;
    private final LocalDateTime createdAt;

    public RecipeDto(final Long id, final String image, final String title, final RecipeAuthorDto author,
                     final List<ProductRecipeDto> products,
                     final Long favoriteCount, final LocalDateTime createdAt) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.products = products;
        this.favoriteCount = favoriteCount;
        this.createdAt = createdAt;
    }

    public static RecipeDto toDto(final Recipe recipe, final List<RecipeImage> recipeImages, final List<Product> products) {
        final RecipeAuthorDto authorDto = RecipeAuthorDto.toDto(recipe.getMember());
        final List<ProductRecipeDto> productDtos = products.stream()
                .map(ProductRecipeDto::toDto)
                .collect(Collectors.toList());
        if (recipeImages.isEmpty()) {
             return new RecipeDto(recipe.getId(), null, recipe.getTitle(), authorDto, productDtos, recipe.getFavoriteCount(), recipe.getCreatedAt());
        }
        return new RecipeDto(recipe.getId(), recipeImages.get(0).getImage(), recipe.getTitle(), authorDto, productDtos, recipe.getFavoriteCount(), recipe.getCreatedAt());
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public RecipeAuthorDto getAuthor() {
        return author;
    }

    public List<ProductRecipeDto> getProducts() {
        return products;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
