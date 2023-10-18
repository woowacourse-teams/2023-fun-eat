package com.funeat.fixture;

import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.PageFixture.평점_내림차순;
import static com.funeat.fixture.PageFixture.평점_오름차순;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.ReviewCreateRequest;
import com.funeat.review.dto.ReviewFavoriteRequest;
import com.funeat.review.dto.SortingReviewRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class ReviewFixture {

    public static final Long 리뷰 = 1L;
    public static final Long 존재하지_않는_리뷰 = 99999L;
    public static final Long 리뷰1 = 1L;
    public static final Long 리뷰2 = 2L;
    public static final Long 리뷰3 = 3L;
    public static final Long 리뷰4 = 4L;
    public static final boolean 좋아요O = true;
    public static final boolean 좋아요X = false;
    public static final boolean 재구매O = true;
    public static final boolean 재구매X = false;

    public static final Long 첫_목록을_가져옴 = 0L;
    public static final boolean 다음_데이터_존재O = true;
    public static final boolean 다음_데이터_존재X = false;

    public static Review 리뷰_이미지test1_평점1점_재구매O_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "test1", 1L, "test", true, count);
    }

    public static Review 리뷰_이미지없음_평점1점_재구매O_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "", 1L, "test", true, count);
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

    public static Review 리뷰_이미지없음_평점1점_재구매X_생성(final Member member, final Product product, final Long count) {
        return new Review(member, product, "", 1L, "test", false, count);
    }

    public static ReviewCreateRequest 리뷰추가요청_생성(final Long rating, final List<Long> tagIds, final String content,
                                                final Boolean rebuy) {
        return new ReviewCreateRequest(rating, tagIds, content, rebuy);
    }

    public static ReviewCreateRequest 리뷰추가요청_재구매O_생성(final Long rating, final List<Long> tagIds) {
        return new ReviewCreateRequest(rating, tagIds, "test", true);
    }

    public static ReviewCreateRequest 리뷰추가요청_재구매X_생성(final Long rating, final List<Long> tagIds) {
        return new ReviewCreateRequest(rating, tagIds, "test", false);
    }

    public static ReviewFavoriteRequest 리뷰좋아요요청_생성(final Boolean favorite) {
        return new ReviewFavoriteRequest(favorite);
    }

    public static SortingReviewRequest 리뷰정렬요청_좋아요수_내림차순_생성(final Long lastReviewId) {
        return new SortingReviewRequest(좋아요수_내림차순, lastReviewId);
    }

    public static SortingReviewRequest 리뷰정렬요청_최신순_생성(final Long lastReviewId) {
        return new SortingReviewRequest(최신순, lastReviewId);
    }

    public static SortingReviewRequest 리뷰정렬요청_평점_오름차순_생성(final Long lastReviewId) {
        return new SortingReviewRequest(평점_오름차순, lastReviewId);
    }

    public static SortingReviewRequest 리뷰정렬요청_평점_내림차순_생성(final Long lastReviewId) {
        return new SortingReviewRequest(평점_내림차순, lastReviewId);
    }

    public static SortingReviewRequest 리뷰정렬요청_존재하지않는정렬_생성() {
        return new SortingReviewRequest("test,test", 1L);
    }
}
