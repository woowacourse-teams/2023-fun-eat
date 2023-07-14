package com.funeat.product.application;

import com.funeat.product.domain.Category;

public class CategoryDto {

    private final Long id;
    private final String name;

    public CategoryDto(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryDto toDto(final Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
