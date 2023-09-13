package com.funeat.member.dto;

import com.funeat.product.domain.Product;

public class MemberRecipeProductDto {

    private final Long id;
    private final String name;

    private MemberRecipeProductDto(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static MemberRecipeProductDto toDto(final Product product) {
        return new MemberRecipeProductDto(product.getId(), product.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
