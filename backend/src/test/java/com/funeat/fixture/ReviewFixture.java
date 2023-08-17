package com.funeat.fixture;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class ReviewFixture {

    public static Review 리뷰_이미지test1_평점1점_재구매O_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test1", 1L, "test", true, count);
    }

    public static Review 리뷰_이미지없음_평점1점_재구매O_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, null, 1L, "test", true, count);
    }

    public static Review 리뷰_이미지test1_평점1점_재구매X_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test1", 1L, "test", false, count);
    }

    public static Review 리뷰_이미지test2_평점2점_재구매O_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test2", 2L, "test", true, count);
    }

    public static Review 리뷰_이미지test2_평점2점_재구매X_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test2", 2L, "test", false, count);
    }

    public static Review 리뷰_이미지test3_평점3점_재구매O_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test3", 3L, "test", true, count);
    }

    public static Review 리뷰_이미지test3_평점3점_재구매X_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test3", 3L, "test", false, count);
    }

    public static Review 리뷰_이미지test4_평점4점_재구매O_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test4", 4L, "test", true, count);
    }

    public static Review 리뷰_이미지test4_평점4점_재구매X_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test4", 4L, "test", false, count);
    }

    public static Review 리뷰_이미지test5_평점5점_재구매O_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test5", 5L, "test", true, count);
    }

    public static Review 리뷰_이미지test5_평점5점_재구매X_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test5", 5L, "test", false, count);
    }

    public static ReviewCreateRequest 리뷰추가요청_재구매O_생성(final Long rating, final List<Long> tagIds) {
        return new ReviewCreateRequest(rating, tagIds, "test", true);
    }

    public static ReviewCreateRequest 리뷰추가요청_재구매X_생성(final Long rating, final List<Long> tagIds) {
        return new ReviewCreateRequest(rating, tagIds, "test", false);
    }

    public static ReviewFavoriteRequest 리뷰좋아요요청_true_생성() {
        return new ReviewFavoriteRequest(true);
    }

    public static ReviewFavoriteRequest 리뷰좋아요요청_false_생성() {
        return new ReviewFavoriteRequest(false);
    }
}
