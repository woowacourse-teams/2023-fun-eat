package com.funeat.product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.funeat.product.domain.Product;
import org.springframework.data.domain.Page;

public class ProductsInCategoryPageDto {
    private final Long totalDataCount;
    private final Long totalPages;
    private final boolean isFirstPage;
    private final boolean isLastPage;
    private final Long requestPage;
    private final Long requestSize;

    @JsonCreator
    public ProductsInCategoryPageDto(final Long totalDataCount,
                                 final Long totalPages,
                                 @JsonProperty(value = "firstPage") final boolean isFirstPage,
                                 @JsonProperty(value = "lastPage") final boolean isLastPage,
                                 final Long requestPage,
                                 final Long requestSize) {
        this.totalDataCount = totalDataCount;
        this.totalPages = totalPages;
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
        this.requestPage = requestPage;
        this.requestSize = requestSize;
    }

    public static ProductsInCategoryPageDto toDto(final Page<Product> page) {
        return new ProductsInCategoryPageDto(
                page.getTotalElements(),
                Long.valueOf(page.getTotalPages()),
                page.isFirst(),
                page.isLast(),
                Long.valueOf(page.getNumber()),
                Long.valueOf(page.getSize())
        );
    }

    public Long getTotalDataCount() {
        return totalDataCount;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public Long getRequestPage() {
        return requestPage;
    }

    public Long getRequestSize() {
        return requestSize;
    }
}
