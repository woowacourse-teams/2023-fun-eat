package com.funeat.fixture;

import com.funeat.common.dto.PageDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@SuppressWarnings("NonAsciiCharacters")
public class PageFixture {

    public static final String 평균_평점_오름차순 = "averageRating,asc";
    public static final String 평균_평점_내림차순 = "averageRating,desc";
    public static final String 가격_오름차순 = "price,asc";
    public static final String 가격_내림차순 = "price,desc";
    public static final String 좋아요수_내림차순 = "favoriteCount,desc";
    public static final String 리뷰수_내림차순 = "reviewCount,desc";
    public static final String 평점_오름차순 = "rating,asc";
    public static final String 평점_내림차순 = "rating,desc";
    public static final String 과거순 = "createdAt,asc";
    public static final String 최신순 = "createdAt,desc";

    public static final Long PAGE_SIZE = 10L;
    public static final Long FIRST_PAGE = 0L;
    public static final Long SECOND_PAGE = 1L;

    public static final boolean 첫페이지O = true;
    public static final boolean 첫페이지X = false;
    public static final boolean 마지막페이지O = true;
    public static final boolean 마지막페이지X = false;

    public static PageRequest 페이지요청_기본_생성(final int page, final int size) {
        return PageRequest.of(page, size);
    }

    public static PageRequest 페이지요청_생성(final int page, final int size, final String sort) {
        final String[] splitSort = sort.split(",");
        final String order = splitSort[0];
        final Direction direction = Direction.fromString(splitSort[1]);

        return PageRequest.of(page, size, Sort.by(direction, order));
    }

    public static PageDto 응답_페이지_생성(final Long totalDataCount, final Long totalPages, final boolean firstPage,
                                    final boolean lastPage, final Long requestPage, final Long requestSize) {
        return new PageDto(totalDataCount, totalPages, firstPage, lastPage, requestPage, requestSize);
    }

    public static Long 총_데이터_개수(final Long count) {
        return count;
    }

    public static Long 총_페이지(final Long page) {
        return page;
    }
}
