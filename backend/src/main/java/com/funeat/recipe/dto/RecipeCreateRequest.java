package com.funeat.recipe.dto;

import java.util.List;

public class RecipeCreateRequest {

    private final String name;
    private final List<Long> productIds;
    private final String content;

    public RecipeCreateRequest(final String name, final List<Long> productIds, final String content) {
        this.name = name;
        this.productIds = productIds;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public String getContent() {
        return content;
    }
}
