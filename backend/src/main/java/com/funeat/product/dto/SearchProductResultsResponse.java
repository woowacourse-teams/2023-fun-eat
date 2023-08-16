package com.funeat.product.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class SearchProductResultsResponse {

    private final PageDto page;
    private final List<SearchProductResultDto> products;

    public SearchProductResultsResponse(final PageDto page, final List<SearchProductResultDto> products) {
        this.page = page;
        this.products = products;
    }

    public static SearchProductResultsResponse toResponse(final PageDto page,
                                                          final List<SearchProductResultDto> products) {
        return new SearchProductResultsResponse(page, products);
    }

    public PageDto getPage() {
        return page;
    }

    public List<SearchProductResultDto> getProducts() {
        return products;
    }
}
