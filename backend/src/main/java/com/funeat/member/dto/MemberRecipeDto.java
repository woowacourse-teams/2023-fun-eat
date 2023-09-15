package com.funeat.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.time.LocalDateTime;
import java.util.List;

public class MemberRecipeDto {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final String image;
    private final Long favoriteCount;
    private final List<MemberRecipeProductDto> products;

    private MemberRecipeDto(final Long id, final String title, final String content, final LocalDateTime createdAt,
                            final Long favoriteCount, final List<MemberRecipeProductDto> products) {
        this(id, title, content, createdAt, null, favoriteCount, products);
    }

    @JsonCreator
    private MemberRecipeDto(final Long id, final String title, final String content, final LocalDateTime createdAt,
                            final String image, final Long favoriteCount, final List<MemberRecipeProductDto> products) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.image = image;
        this.favoriteCount = favoriteCount;
        this.products = products;
    }

    public static MemberRecipeDto toDto(final Recipe recipe, final List<RecipeImage> findRecipeImages,
                                        final List<MemberRecipeProductDto> memberRecipeProductDtos) {
        if (findRecipeImages.isEmpty()) {
            return new MemberRecipeDto(recipe.getId(), recipe.getTitle(), recipe.getContent(), recipe.getCreatedAt(),
                    recipe.getFavoriteCount(), memberRecipeProductDtos);
        }
        return new MemberRecipeDto(recipe.getId(), recipe.getTitle(), recipe.getContent(), recipe.getCreatedAt(),
                findRecipeImages.get(0).getImage(), recipe.getFavoriteCount(), memberRecipeProductDtos);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getImage() {
        return image;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public List<MemberRecipeProductDto> getProducts() {
        return products;
    }
}
