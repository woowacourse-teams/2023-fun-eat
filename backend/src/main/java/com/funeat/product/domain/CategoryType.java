package com.funeat.product.domain;

public enum CategoryType {
    FOOD, STORE;

    public static String convertToLowerCase(final CategoryType type) {
        return type.name().toLowerCase();
    }
}
