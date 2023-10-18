package com.funeat.product.dto;

public class ProductSortCondition {

    private final String by;
    private final String order;

    private ProductSortCondition(final String by, final String order) {
        this.by = by;
        this.order = order;
    }

    public static ProductSortCondition toDto(final String sort) {
        final String[] split = sort.split(",");
        return new ProductSortCondition(split[0], split[1]);
    }

    public String getBy() {
        return by;
    }

    public String getOrder() {
        return order;
    }
}
