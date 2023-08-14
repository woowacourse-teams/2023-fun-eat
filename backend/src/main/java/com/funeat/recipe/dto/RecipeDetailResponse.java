package com.funeat.recipe.dto;

import com.funeat.product.domain.Product;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private final LocalDateTime createdAt;

    public RecipeDetailResponse(final Long id, final List<String> images, final String title, final String content,
                                final RecipeAuthorDto author,
                                final List<ProductRecipeDto> products, final Long totalPrice, final Long favoriteCount,
                                final Boolean favorite,
                                final LocalDateTime createdAt) {
        this.id = id;
        this.images = images;
        this.title = title;
        this.content = content;
        this.author = author;
        this.products = products;
        this.totalPrice = totalPrice;
        this.favoriteCount = favoriteCount;
        this.favorite = favorite;
        this.createdAt = createdAt;
    }

    public static RecipeDetailResponse toResponse(final Recipe recipe, final List<RecipeImage> recipeImages,
                                                  final List<Product> products, final Long totalPrice,
                                                  final Boolean favorite) {
        final RecipeAuthorDto authorDto = RecipeAuthorDto.toDto(recipe.getMember());
        final List<ProductRecipeDto> productDtos = products.stream()
                .map(ProductRecipeDto::toDto)
                .collect(Collectors.toList());
        final List<String> images = recipeImages.stream()
                .map(RecipeImage::getImage)
                .collect(Collectors.toList());
        return new RecipeDetailResponse(recipe.getId(), images, recipe.getTitle(), recipe.getContent(),
                authorDto, productDtos, totalPrice, recipe.getFavoriteCount(), favorite, recipe.getCreatedAt());
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
