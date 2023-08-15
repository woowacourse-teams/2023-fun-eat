package com.funeat.product.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class ProductsInCategoryResponse {

    private final PageDto page;
    private final List<ProductInCategoryDto> products;

    public ProductsInCategoryResponse(final PageDto page, final List<ProductInCategoryDto> products) {
        this.page = page;
        this.products = products;
    }

    public static ProductsInCategoryResponse toResponse(final PageDto page, final List<ProductInCategoryDto> products) {
        return new ProductsInCategoryResponse(page, products);
    }

    public PageDto getPage() {
        return page;
    }

    public List<ProductInCategoryDto> getProducts() {
        return products;
    }
}
