package com.funeat.product.dto;

import com.funeat.product.domain.Product;

public class ProductReviewCountDto {

    private final Product product;
    private final Long reviewCount;

    public ProductReviewCountDto(final Product product, final Long reviewCount) {
        this.product = product;
        this.reviewCount = reviewCount;
    }

    public Product getProduct() {
        return product;
    }

    public Long getReviewCount() {
        return reviewCount;
    }
}
