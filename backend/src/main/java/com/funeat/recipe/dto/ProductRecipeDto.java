package com.funeat.recipe.dto;

public class ProductRecipeDto {

    private final Long id;
    private final String name;
    private final Long price;

    public ProductRecipeDto(final Long id, final String name, final Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }
}
