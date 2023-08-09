package com.funeat.fixture;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SuppressWarnings("NonAsciiCharacters")
public class PageFixture {

    private static final String 평점 = "averageRating";
    private static final String 가격 = "price";
    private static final String 등록 = "createdAt";

    public static PageRequest 페이지요청_기본_생성(final int page, final int size) {
        return PageRequest.of(page, size);
    }

    public static PageRequest 페이지요청_평점_오름차순_생성(final int page, final int size) {
        final var sort = Sort.by(평점).ascending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_평점_내림차순_생성(final int page, final int size) {
        final var sort = Sort.by(평점).descending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_가격_오름차순_생성(final int page, final int size) {
        final var sort = Sort.by(가격).ascending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_가격_내림차순_생성(final int page, final int size) {
        final var sort = Sort.by(가격).descending();

        return PageRequest.of(page, size, sort);
    }
}
