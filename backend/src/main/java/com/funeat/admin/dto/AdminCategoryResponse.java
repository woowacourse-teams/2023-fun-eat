package com.funeat.admin.dto;

import com.funeat.product.domain.Category;

public class AdminCategoryResponse {

    private final Long id;
    private final String name;
    private final String image;

    public AdminCategoryResponse(final Long id, final String name, final String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static AdminCategoryResponse toResponse(final Category category) {
        return new AdminCategoryResponse(category.getId(), category.getName(), category.getImage());
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
