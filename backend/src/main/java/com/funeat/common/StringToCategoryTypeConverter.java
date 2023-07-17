package com.funeat.common;

import com.funeat.product.domain.CategoryType;
import org.springframework.core.convert.converter.Converter;

public class StringToCategoryTypeConverter implements Converter<String, CategoryType> {

    @Override
    public CategoryType convert(final String source) {
        return CategoryType.valueOf(source.toUpperCase());
    }
}
