package com.funeat.fixture;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SuppressWarnings("NonAsciiCharacters")
public class PageFixture {

    public static final String 평균_평점_오름차순 = "averageRating,asc";
    public static final String 평균_평점_내림차순 = "averageRating,desc";
    public static final String 가격_오름차순 = "price,asc";
    public static final String 가격_내림차순 = "price,desc";
    public static final String 좋아요_내림차순 = "favoriteCount,desc";
    public static final String 평점_오름차순 = "rating,asc";
    public static final String 평점_내림차순 = "rating,desc";
    public static final String 최신순 = "createdAt,desc";

    public static final Long PAGE_SIZE = 10L;
    public static final Long FIRST_PAGE = 0L;
    public static final Long SECOND_PAGE = 1L;

    public static PageRequest 페이지요청_기본_생성(final int page, final int size) {
        return PageRequest.of(page, size);
    }

    public static PageRequest 페이지요청_생성(final int page, final int size, final String sort) {
        final String[] splitSort = sort.split(",");
        final String order = splitSort[0];
        final String direction = splitSort[1];

        return PageRequest.of(page, size, Sort.by(direction, order));
    }
}
