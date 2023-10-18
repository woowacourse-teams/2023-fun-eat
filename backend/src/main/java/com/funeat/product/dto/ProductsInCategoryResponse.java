package com.funeat.product.dto;

import java.util.List;

public class ProductsInCategoryResponse {

    private final boolean hasNext;
    private final List<ProductInCategoryDto> products;

    public ProductsInCategoryResponse(final boolean hasNext, final List<ProductInCategoryDto> products) {
        this.hasNext = hasNext;
        this.products = products;
    }

    public static ProductsInCategoryResponse toResponse(final boolean hasNext,
                                                        final List<ProductInCategoryDto> products) {
        return new ProductsInCategoryResponse(hasNext, products);
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public List<ProductInCategoryDto> getProducts() {
        return products;
    }
}
