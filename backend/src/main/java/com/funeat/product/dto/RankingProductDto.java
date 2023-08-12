package com.funeat.product.dto;

import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;

public class RankingProductDto {

    private final Long id;
    private final String name;
    private final String image;
    private final String categoryType;

    public RankingProductDto(final Long id, final String name, final String image, final String categoryType) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.categoryType = categoryType;
    }

    public static RankingProductDto toDto(final Product product) {
        return new RankingProductDto(product.getId(), product.getName(), product.getImage(),
                CategoryType.convertToLowerCase(product.getCategory().getType()));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCategoryType() {
        return categoryType;
    }
}
