package com.funeat.admin.dto;

import com.funeat.product.domain.Product;

public class AdminProductResponse {

    private final Long id;
    private final String name;
    private final String content;
    private final Long price;
    private final AdminCategoryResponse categoryResponse;

    private AdminProductResponse(final Long id, final String name, final String content,
                                 final Long price, final AdminCategoryResponse categoryResponse) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
        this.categoryResponse = categoryResponse;
    }

    public static AdminProductResponse toResponse(final Product product) {
        final AdminCategoryResponse categoryResponse = AdminCategoryResponse.toResponse(product.getCategory());

        return new AdminProductResponse(product.getId(), product.getName(), product.getContent(), product.getPrice(),
                categoryResponse);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Long getPrice() {
        return price;
    }

    public AdminCategoryResponse getCategoryResponse() {
        return categoryResponse;
    }
}
