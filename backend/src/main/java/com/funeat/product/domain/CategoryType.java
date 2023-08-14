package com.funeat.product.domain;

public enum CategoryType {

    FOOD("food"),
    STORE("store"),
    ;

    private final String name;

    CategoryType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
