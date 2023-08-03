package com.funeat.product.dto;

import java.util.List;

public class RankingProductsResponse {

    private final List<RankingProductDto> products;

    public RankingProductsResponse(final List<RankingProductDto> products) {
        this.products = products;
    }

    public static RankingProductsResponse toResponse(final List<RankingProductDto> products) {
        return new RankingProductsResponse(products);
    }

    public List<RankingProductDto> getProducts() {
        return products;
    }
}
