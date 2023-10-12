package com.funeat.admin.dto;

public class ProductUpdateRequest {

    private final String name;
    private final Long price;
    private final String content;
    private final Long categoryId;

    public ProductUpdateRequest(final String name, final Long price, final String content, final Long categoryId) {
        this.name = name;
        this.price = price;
        this.content = content;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getContent() {
        return content;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
