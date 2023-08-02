package com.funeat.product.dto;

import com.funeat.product.domain.Product;

public class RankingProductDto {

    private final Long id;
    private final String name;
    private final String image;

    public RankingProductDto(final Long id, final String name, final String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static RankingProductDto toDto(final Product product) {
        return new RankingProductDto(product.getId(), product.getName(), product.getImage());
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
