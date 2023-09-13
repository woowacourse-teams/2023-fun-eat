package com.funeat.product.domain;

import static com.funeat.product.exception.CategoryErrorCode.CATEGORY_TYPE_NOT_FOUND;

import com.funeat.product.exception.CategoryException.CategoryTypeNotFoundException;
import java.util.Arrays;

public enum CategoryType {

    FOOD("food"),
    STORE("store"),
    ;

    private final String name;

    CategoryType(final String name) {
        this.name = name;
    }

    public static CategoryType findCategoryType(final String type) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(type.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new CategoryTypeNotFoundException(CATEGORY_TYPE_NOT_FOUND, type));
    }

    public String getName() {
        return name;
    }
}
