package com.funeat.recipe.dto;

import java.util.List;

public class RecipeCreateRequest {

    private final String title;
    private final List<Long> productIds;
    private final String content;

    public RecipeCreateRequest(final String title, final List<Long> productIds, final String content) {
        this.title = title;
        this.productIds = productIds;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public String getContent() {
        return content;
    }
}
