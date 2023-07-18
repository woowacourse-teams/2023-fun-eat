package com.funeat.product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.funeat.product.domain.Product;
import org.springframework.data.domain.Page;

public class ProductsInCategoryPageDto {
    private final Long totalDataCount;
    private final Long totalPages;
    private final boolean firstPage;
    private final boolean lastPage;
    private final Long requestPage;
    private final Long requestSize;

    @JsonCreator
    public ProductsInCategoryPageDto(final Long totalDataCount,
                                 final Long totalPages,
                                 final boolean FirstPage,
                                 final boolean LastPage,
                                 final Long requestPage,
                                 final Long requestSize) {
        this.totalDataCount = totalDataCount;
        this.totalPages = totalPages;
        this.firstPage = FirstPage;
        this.lastPage = LastPage;
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
        return firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public Long getRequestPage() {
        return requestPage;
    }

    public Long getRequestSize() {
        return requestSize;
    }
}
