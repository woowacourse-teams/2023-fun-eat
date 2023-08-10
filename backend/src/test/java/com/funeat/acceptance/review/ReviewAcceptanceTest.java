package com.funeat.acceptance.review;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static com.funeat.acceptance.review.ReviewSteps.단일_리뷰_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_랭킹_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_사진_명세_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.정렬된_리뷰_목록_조회_요청;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_false_생성;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_true_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_푸짐해요_PRICE_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Category;
import com.funeat.review.domain.Review;
import com.funeat.review.presentation.dto.RankingReviewDto;
import com.funeat.review.presentation.dto.SortingReviewDto;
import com.funeat.review.presentation.dto.SortingReviewsPageDto;
import com.funeat.tag.domain.Tag;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewAcceptanceTest extends AcceptanceTest {

    @Nested
    class writeReview_성공_테스트 {

        @Test
        void 리뷰를_작성한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            카테고리_단일_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 리뷰_사진_명세_요청();
            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 단일_리뷰_요청(productId, image, request, loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }
    }

    @Nested
    class toggleLikeReview_성공_테스트 {

        @Test
        void 리뷰에_좋아요를_할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            카테고리_단일_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 리뷰_사진_명세_요청();
            final var reviewRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();
            단일_리뷰_요청(productId, image, reviewRequest, loginCookie);

            final var reviewId = reviewRepository.findAll().get(0).getId();
            final var favoriteRequest = 리뷰좋아요요청_true_생성();

            // when
            final var response = 리뷰_좋아요_요청(productId, reviewId, favoriteRequest, loginCookie);
            final var actual = reviewFavoriteRepository.findAll().get(0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
            리뷰_좋아요_결과를_검증한다(actual, memberId, reviewId, true);
        }

        @Test
        void 리뷰에_좋아요를_취소할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            카테고리_단일_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_푸짐해요_PRICE_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var image = 리뷰_사진_명세_요청();
            final var reviewRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            final var loginCookie = 로그인_쿠키를_얻는다();

            단일_리뷰_요청(productId, image, reviewRequest, loginCookie);

            final var reviewId = reviewRepository.findAll().get(0).getId();

            final var favoriteRequest = 리뷰좋아요요청_true_생성();
            리뷰_좋아요_요청(productId, reviewId, favoriteRequest, loginCookie);

            final var favoriteCancelRequest = 리뷰좋아요요청_false_생성();

            // when
            final var response = 리뷰_좋아요_요청(productId, reviewId, favoriteCancelRequest, loginCookie);
            final var actual = reviewFavoriteRepository.findAll().get(0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
            리뷰_좋아요_결과를_검증한다(actual, memberId, reviewId, false);
        }
    }

    @Nested
    class getSortingReviews_성공_테스트 {

        @Nested
        class 좋아요_기준_내림차순으로_리뷰_목록_조회 {

            @Test
            void 좋아요_수가_서로_다르면_좋아요_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                카테고리_단일_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review2, review3, review1);
                final var pageDto = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);

                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "favoriteCount,desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, pageDto, member1);
            }

            @Test
            void 좋아요_수가_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                카테고리_단일_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 130L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 130L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review3, review2, review1);
                final var pageDto = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "favoriteCount,desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, pageDto, member1);
            }
        }

        @Nested
        class 평점_기준_오름차순으로_리뷰_목록을_조회 {

            @Test
            void 평점이_서로_다르면_평점_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                카테고리_단일_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test2_평점2점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review1, review3, review2);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,asc", 0);
                final var page = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }

            @Test
            void 평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                카테고리_단일_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review3, review2, review1);
                final var page = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,asc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }
        }

        @Nested
        class 평점_기준_내림차순으로_리뷰_목록_조회 {

            @Test
            void 평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                카테고리_단일_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test2_평점2점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review2, review3, review1);
                final var page = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }

            @Test
            void 평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                카테고리_단일_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review3, review2, review1);
                final var page = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }
        }

        @Nested
        class 최신순으로_리뷰_목록을_조회 {

            @Test
            void 등록_시간이_서로_다르면_최신순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                카테고리_단일_저장(category);

                final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var productId = 단일_상품_저장(product);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1 = 리뷰_이미지test2_평점2점_재구매O_생성(member1, product, 5L);
                final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 351L);
                final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
                복수_리뷰_저장(review1, review2, review3);

                final var sortingReviews = List.of(review3, review2, review1);
                final var loginCookie = 로그인_쿠키를_얻는다();

                // when
                final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "createdAt,desc", 0);
                final var page = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
            }
        }
    }

    @Nested
    class getRankingReviews_성공_테스트 {

        @Test
        void 리뷰_랭킹을_조회하다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            카테고리_단일_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var review1_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 5L);
            final var review1_2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product1, 351L);
            final var review1_3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product1, 130L);
            final var review2_1 = 리뷰_이미지test5_평점5점_재구매O_생성(member1, product2, 247L);
            final var review2_2 = 리뷰_이미지test1_평점1점_재구매X_생성(member2, product2, 83L);
            복수_리뷰_저장(review1_1, review1_2, review1_3, review2_1, review2_2);

            final var rankingReviews = List.of(review1_2, review2_1, review1_3);

            // when
            final var response = 리뷰_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            리뷰_랭킹_조회_결과를_검증한다(response, rankingReviews);
        }
    }

    private void 리뷰_좋아요_결과를_검증한다(final ReviewFavorite actual, final Long expectedMemberId,
                                 final Long expectedReviewId, final Boolean expectedFavorite) {
        final var actualId = actual.getId();
        final var actualMemberId = actual.getMember().getId();
        final var actualReviewId = actual.getReview().getId();
        final var actualFavorite = actual.getFavorite();

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualId)
                    .isNotNull();
            softAssertions.assertThat(actualReviewId)
                    .isEqualTo(expectedReviewId);
            softAssertions.assertThat(actualMemberId)
                    .isEqualTo(expectedMemberId);
            softAssertions.assertThat(actualFavorite)
                    .isEqualTo(expectedFavorite);
        });
    }

    private Long 카테고리_단일_저장(final Category category) {
        return categoryRepository.save(category).getId();
    }

    private List<Long> 태그_아이디_변환(final Tag... tags) {
        return Stream.of(tags)
                .map(Tag::getId)
                .collect(Collectors.toList());
    }

    private void 정렬된_리뷰_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews,
                                       final SortingReviewsPageDto pageDto, final Member member) {
        페이지를_검증한다(response, pageDto);
        리뷰_목록을_검증한다(response, reviews, member);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final SortingReviewsPageDto expected) {
        final var actual = response.jsonPath().getObject("page", SortingReviewsPageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 리뷰_목록을_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews,
                             final Member member) {
        final var expected = reviews.stream()
                .map(review -> SortingReviewDto.toDto(review, member))
                .collect(Collectors.toList());
        final var actual = response.jsonPath().getList("reviews", SortingReviewDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 리뷰_랭킹_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews) {
        final var expected = reviews.stream()
                .map(RankingReviewDto::toDto)
                .collect(Collectors.toList());
        final var actual = response.jsonPath()
                .getList("reviews", RankingReviewDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
