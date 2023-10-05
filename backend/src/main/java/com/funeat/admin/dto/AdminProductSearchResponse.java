package com.funeat.admin.dto;

import java.util.List;

public class AdminProductSearchResponse {

    private final List<AdminProductResponse> productResponses;
    private final Long totalElements;
    private final Boolean isLastPage;

    public AdminProductSearchResponse(final List<AdminProductResponse> productResponses, final Long totalElements,
                                      final Boolean isLastPage) {
        this.productResponses = productResponses;
        this.totalElements = totalElements;
        this.isLastPage = isLastPage;
    }

    public List<AdminProductResponse> getProductResponses() {
        return productResponses;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Boolean isLastPage() {
        return isLastPage;
    }
}
