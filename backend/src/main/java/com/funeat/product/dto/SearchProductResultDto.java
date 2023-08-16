package com.funeat.product.dto;

import com.funeat.product.domain.Product;

public class SearchProductResultDto {

    private final Long id;
    private final String name;
    private final Long price;
    private final String image;
    private final Double averageRating;
    private final Long reviewCount;
    private final String categoryType;

    public SearchProductResultDto(final Long id, final String name, final Long price, final String image,
                                  final Double averageRating, final Long reviewCount, final String categoryType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.categoryType = categoryType;
    }

    public static SearchProductResultDto toDto(final Product product, final Long reviewCount) {
        return new SearchProductResultDto(product.getId(), product.getName(), product.getPrice(), product.getImage(),
                product.getAverageRating(), reviewCount, product.getCategory().getType().getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public String getCategoryType() {
        return categoryType;
    }
}
