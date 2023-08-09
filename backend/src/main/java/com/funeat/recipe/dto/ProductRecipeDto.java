package com.funeat.recipe.dto;

import com.funeat.product.domain.Product;

public class ProductRecipeDto {

    private final Long id;
    private final String name;
    private final Long price;

    private ProductRecipeDto(final Long id, final String name, final Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductRecipeDto toDto(final Product product) {
        return new ProductRecipeDto(product.getId(), product.getName(), product.getPrice());
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
}
