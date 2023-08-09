package com.funeat.fixture;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;

@SuppressWarnings("NonAsciiCharacters")
public class ReviewFixture {

    public static Review 리뷰_평점1점_재구매O_생성(final Member member, final Product product) {
        return new Review(member, product, "1점", 1L, "test", true);
    }

    public static Review 리뷰_평점1점_재구매X_생성(final Member member, final Product product) {
        return new Review(member, product, "1점", 1L, "test", false);
    }

    public static Review 리뷰_평점2점_재구매O_생성(final Member member, final Product product) {
        return new Review(member, product, "2점", 2L, "test", true);
    }

    public static Review 리뷰_평점2점_재구매X_생성(final Member member, final Product product) {
        return new Review(member, product, "2점", 2L, "test", false);
    }

    public static Review 리뷰_평점3점_재구매O_생성(final Member member, final Product product) {
        return new Review(member, product, "3점", 3L, "test", true);
    }

    public static Review 리뷰_평점3점_재구매X_생성(final Member member, final Product product) {
        return new Review(member, product, "3점", 3L, "test", false);
    }

    public static Review 리뷰_평점4점_재구매O_생성(final Member member, final Product product) {
        return new Review(member, product, "4점", 4L, "test", true);
    }

    public static Review 리뷰_평점4점_재구매X_생성(final Member member, final Product product) {
        return new Review(member, product, "4점", 4L, "test", false);
    }

    public static Review 리뷰_평점5점_재구매O_생성(final Member member, final Product product) {
        return new Review(member, product, "5점", 5L, "test", true);
    }

    public static Review 리뷰_평점5점_재구매X_생성(final Member member, final Product product) {
        return new Review(member, product, "5점", 5L, "test", false);
    }
}
