package com.funeat.product.presentation;

import com.funeat.product.application.CategoryDto;
import com.funeat.product.domain.Category;

public class CategoryResponse {

    private final Long id;
    private final String name;

    public CategoryResponse(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryResponse toResponse(final Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

    public static CategoryResponse toResponse(final CategoryDto categoryDto) {
        return new CategoryResponse(categoryDto.getId(), categoryDto.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
