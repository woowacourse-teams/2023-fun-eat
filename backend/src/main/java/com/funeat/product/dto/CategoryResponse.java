package com.funeat.product.dto;

import com.funeat.product.domain.Category;

public class CategoryResponse {

    private final Long id;
    private final String name;
    private final String image;

    public CategoryResponse(final Long id, final String name, final String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static CategoryResponse toResponse(final Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getImage());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
