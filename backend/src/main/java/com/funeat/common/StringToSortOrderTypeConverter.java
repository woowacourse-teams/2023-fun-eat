package com.funeat.common;

import com.funeat.product.domain.SortOrderType;
import org.springframework.core.convert.converter.Converter;

public class StringToSortOrderTypeConverter implements Converter<String, SortOrderType> {

    @Override
    public SortOrderType convert(final String source) {
        return SortOrderType.valueOf(source.toUpperCase());
    }
}
