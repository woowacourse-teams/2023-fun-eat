package com.funeat.product.dto;

import java.util.List;

public class ProductsInCategoryResponse {

    private final ProductsInCategoryPageDto page;
    private final List<ProductInCategoryDto> products;

    public ProductsInCategoryResponse(final ProductsInCategoryPageDto page, final List<ProductInCategoryDto> products) {
        this.page = page;
        this.products = products;
    }

    public ProductsInCategoryPageDto getPage() {
        return page;
    }

    public List<ProductInCategoryDto> getProducts() {
        return products;
    }
}
