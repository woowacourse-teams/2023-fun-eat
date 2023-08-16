package com.funeat.product.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class SearchProductsResponse {

    private final PageDto page;
    private final List<SearchProductDto> products;

    public SearchProductsResponse(final PageDto page, final List<SearchProductDto> products) {
        this.page = page;
        this.products = products;
    }

    public static SearchProductsResponse toResponse(final PageDto page, final List<SearchProductDto> products) {
        return new SearchProductsResponse(page, products);
    }

    public PageDto getPage() {
        return page;
    }

    public List<SearchProductDto> getProducts() {
        return products;
    }
}
