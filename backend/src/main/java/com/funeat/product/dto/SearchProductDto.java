package com.funeat.product.dto;

import com.funeat.product.domain.Product;

public class SearchProductDto {

    private final Long id;
    private final String name;
    private final String categoryType;

    public SearchProductDto(final Long id, final String name, final String categoryType) {
        this.id = id;
        this.name = name;
        this.categoryType = categoryType;
    }

    public static SearchProductDto toDto(final Product product) {
        return new SearchProductDto(product.getId(), product.getName(), product.getCategory().getType().getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategoryType() {
        return categoryType;
    }
}
