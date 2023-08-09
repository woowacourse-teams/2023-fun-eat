package com.funeat.fixture;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SuppressWarnings("NonAsciiCharacters")
public class PageFixture {

    private static final String 평균_평점 = "averageRating";
    private static final String 가격 = "price";
    private static final String 좋아요 = "favoriteCount";
    private static final String 평점 = "rating";
    private static final String 생성_시간 = "createdAt";

    public static PageRequest 페이지요청_기본_생성(final int page, final int size) {
        return PageRequest.of(page, size);
    }

    public static PageRequest 페이지요청_평균_평점_오름차순_생성(final int page, final int size) {
        final var sort = Sort.by(평균_평점).ascending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_평균_평점_내림차순_생성(final int page, final int size) {
        final var sort = Sort.by(평균_평점).descending();

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

    public static PageRequest 페이지요청_좋아요_오름차순_생성(final int page, final int size) {
        final var sort = Sort.by(좋아요).ascending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_좋아요_내림차순_생성(final int page, final int size) {
        final var sort = Sort.by(좋아요).descending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_평점_오름차순_생성(final int page, final int size) {
        final var sort = Sort.by(평점).ascending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_평점_내림차순_생성(final int page, final int size) {
        final var sort = Sort.by(평점).descending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_생성_시간_오름차순_생성(final int page, final int size) {
        final var sort = Sort.by(생성_시간).ascending();

        return PageRequest.of(page, size, sort);
    }

    public static PageRequest 페이지요청_생성_시간_내림차순_생성(final int page, final int size) {
        final var sort = Sort.by(생성_시간).descending();

        return PageRequest.of(page, size, sort);
    }
}
