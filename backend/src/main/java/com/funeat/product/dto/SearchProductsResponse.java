package com.funeat.product.dto;

import java.util.List;

public class SearchProductsResponse {

    private final boolean hasNext;
    private final List<SearchProductDto> products;

    private SearchProductsResponse(final boolean hasNext, final List<SearchProductDto> products) {
        this.hasNext = hasNext;
        this.products = products;
    }

    public static SearchProductsResponse toResponse(final boolean hasNext, final List<SearchProductDto> products) {
        return new SearchProductsResponse(hasNext, products);
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public List<SearchProductDto> getProducts() {
        return products;
    }
}
