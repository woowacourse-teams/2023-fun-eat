package com.funeat.common;

import com.funeat.product.domain.SortType;
import org.springframework.core.convert.converter.Converter;

public class StringToSortTypeConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(final String source) {
        return SortType.valueOf(source.toUpperCase());
    }
}
