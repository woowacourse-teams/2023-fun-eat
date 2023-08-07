package com.funeat.acceptance.review;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_랭킹_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_사진_명세_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_추가_요청;
import static com.funeat.acceptance.review.ReviewSteps.정렬된_리뷰_목록_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.presentation.dto.RankingReviewDto;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import com.funeat.review.presentation.dto.SortingReviewDto;
import com.funeat.review.presentation.dto.SortingReviewsPageDto;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.domain.TagType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewAcceptanceTest extends AcceptanceTest {

    @Test
    void 리뷰를_작성한다() {
        // given
        final var savedProductId = 상품_추가_요청(new Product("testName", 1000L, "test.png", "test", null));
        final var savedTagIds = 태그_추가_요청();
        final var image = 리뷰_사진_명세_요청();
        final var loginCookie = 로그인_쿠키를_얻는다();

        final var request = new ReviewCreateRequest(4L, savedTagIds, "test content", true);

        // when
        final var response = 리뷰_추가_요청(savedProductId, image, request, loginCookie);

        // then
        STATUS_CODE를_검증한다(response, 정상_생성);
    }

    @Test
    void 리뷰에_좋아요를_할_수_있다() {
        // given
        final var savedMemberId = 유저_추가_요청(new Member("test", "image.png", "1"));
        final var savedProductId = 상품_추가_요청(new Product("testName", 1000L, "test.png", "test", null));
        final var savedTagIds = 태그_추가_요청();
        final var image = 리뷰_사진_명세_요청();
        final var reviewRequest = new ReviewCreateRequest(4L, savedTagIds, "test content", true);
        final var favoriteRequest = new ReviewFavoriteRequest(true);

        final var loginCookie = 로그인_쿠키를_얻는다();

        리뷰_추가_요청(savedProductId, image, reviewRequest, loginCookie);
        final var savedReviewId = reviewRepository.findAll().get(0).getId();

        // when
        final var response = 리뷰_좋아요_요청(savedProductId, savedReviewId, favoriteRequest, loginCookie);
        final var result = reviewFavoriteRepository.findAll().get(0);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
        리뷰_좋아요_결과를_검증한다(result, savedMemberId, savedReviewId);
        assertThat(result.getFavorite()).isTrue();
    }

    @Test
    void 리뷰에_좋아요를_취소할_수_있다() {
        // given
        final var savedMemberId = 유저_추가_요청(new Member("test", "image.png", "1"));
        final var savedProductId = 상품_추가_요청(new Product("testName", 1000L, "test.png", "test", null));
        final var savedTagIds = 태그_추가_요청();
        final var image = 리뷰_사진_명세_요청();
        final var reviewRequest = new ReviewCreateRequest(4L, savedTagIds, "test content", true);
        final var favoriteRequest = new ReviewFavoriteRequest(true);
        final var favoriteCancelRequest = new ReviewFavoriteRequest(false);
        final var loginCookie = 로그인_쿠키를_얻는다();

        리뷰_추가_요청(savedProductId, image, reviewRequest, loginCookie);
        final var savedReview = reviewRepository.findAll().get(0);
        리뷰_좋아요_요청(savedProductId, savedReview.getId(), favoriteRequest, loginCookie);

        // when
        final var response = 리뷰_좋아요_요청(savedProductId, savedReview.getId(), favoriteCancelRequest, loginCookie);
        final var result = reviewFavoriteRepository.findAll().get(0);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
        리뷰_좋아요_결과를_검증한다(result, savedMemberId, savedReview.getId());
        assertThat(result.getFavorite()).isFalse();
    }

    @Nested
    class 좋아요_기준_내림차순으로_리뷰_목록_조회 {

        @Test
        void 좋아요_수가_서로_다르면_좋아요_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var category = new Category("간편식사", CategoryType.FOOD);
            카테고리_추가_요청(category);

            final var member1 = new Member("test1", "test1.png", "1");
            final var member2 = new Member("test2", "test2.png", "2'");
            final var member3 = new Member("test3", "test3.png", "3'");
            final var members = List.of(member1, member2, member3);
            복수_유저_추가_요청(members);

            final var product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
            final var productId = 상품_추가_요청(product);

            final var review1 = new Review(member1, product, "review1.jpg", 3L, "이 김밥은 재밌습니다", true, 5L);
            final var review2 = new Review(member2, product, "review2.jpg", 4L, "역삼역", true, 351L);
            final var review3 = new Review(member3, product, "review3.jpg", 3L, "ㅇㅇ", false, 130L);
            final var reviews = List.of(review1, review2, review3);
            복수_리뷰_추가(reviews);

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
            final var category = new Category("간편식사", CategoryType.FOOD);
            카테고리_추가_요청(category);

            final var member1 = new Member("test1", "test1.png", "1");
            final var member2 = new Member("test2", "test2.png", "2");
            final var member3 = new Member("test3", "test3.png", "3");
            final var member4 = new Member("test4", "test4.png", "4");
            final var members = List.of(member1, member2, member3, member4);
            복수_유저_추가_요청(members);

            final var product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
            final var productId = 상품_추가_요청(product);

            final var review1 = new Review(member1, product, "review1.jpg", 3L, "이 김밥은 재밌습니다", true, 130L);
            final var review2 = new Review(member2, product, "review2.jpg", 4L, "역삼역", true, 130L);
            final var review3 = new Review(member3, product, "review3.jpg", 3L, "토미토", false, 130L);
            final var review4 = new Review(member4, product, "review4.jpg", 4L, "기러기", false, 130L);
            final var reviews = List.of(review1, review2, review3, review4);
            복수_리뷰_추가(reviews);

            final var sortingReviews = List.of(review4, review3, review2, review1);
            final var pageDto = new SortingReviewsPageDto(4L, 1L, true, true, 0L, 10L);
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
            final var category = new Category("간편식사", CategoryType.FOOD);
            카테고리_추가_요청(category);

            final var member1 = new Member("test1", "test1.png", "1");
            final var member2 = new Member("test2", "test2.png", "2");
            final var member3 = new Member("test3", "test3.png", "3");
            final var members = List.of(member1, member2, member3);
            복수_유저_추가_요청(members);

            final var product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
            final var productId = 상품_추가_요청(product);

            final var review1 = new Review(member1, product, "review1.jpg", 2L, "이 김밥은 재밌습니다", true, 5L);
            final var review2 = new Review(member2, product, "review2.jpg", 4L, "역삼역", true, 351L);
            final var review3 = new Review(member3, product, "review3.jpg", 3L, "ㅇㅇ", false, 130L);
            final var reviews = List.of(review1, review2, review3);
            복수_리뷰_추가(reviews);

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
            final var category = new Category("간편식사", CategoryType.FOOD);
            카테고리_추가_요청(category);

            final var member1 = new Member("test1", "test1.png", "1");
            final var member2 = new Member("test2", "test2.png", "2");
            final var member3 = new Member("test3", "test3.png", "3");
            final var member4 = new Member("test4", "test4.png", "4");
            final var members = List.of(member1, member2, member3, member4);
            복수_유저_추가_요청(members);

            final var product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
            final var productId = 상품_추가_요청(product);

            final var review1 = new Review(member1, product, "review1.jpg", 3L, "이 김밥은 재밌습니다", true, 5L);
            final var review2 = new Review(member2, product, "review2.jpg", 3L, "역삼역", true, 351L);
            final var review3 = new Review(member3, product, "review3.jpg", 3L, "토마토", false, 130L);
            final var review4 = new Review(member4, product, "review4.jpg", 3L, "기러기", false, 130L);
            final var reviews = List.of(review1, review2, review3, review4);
            복수_리뷰_추가(reviews);

            final var sortingReviews = List.of(review4, review3, review2, review1);
            final var page = new SortingReviewsPageDto(4L, 1L, true, true, 0L, 10L);
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,asc", 0);

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
            final var category = new Category("간편식사", CategoryType.FOOD);
            카테고리_추가_요청(category);

            final var member1 = new Member("test1", "test1.png", "1");
            final var member2 = new Member("test2", "test2.png", "2");
            final var member3 = new Member("test3", "test3.png", "3");
            final var members = List.of(member1, member2, member3);
            복수_유저_추가_요청(members);

            final var product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
            final var productId = 상품_추가_요청(product);

            final var review1 = new Review(member1, product, "review1.jpg", 2L, "이 김밥은 재밌습니다", true, 5L);
            final var review2 = new Review(member2, product, "review2.jpg", 4L, "역삼역", true, 351L);
            final var review3 = new Review(member3, product, "review3.jpg", 3L, "ㅇㅇ", false, 130L);
            final var reviews = List.of(review1, review2, review3);
            복수_리뷰_추가(reviews);

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

    @Nested
    class 평점_기준_내림차순으로_리뷰_목록_조회 {

        @Test
        void 평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var category = new Category("간편식사", CategoryType.FOOD);
            카테고리_추가_요청(category);

            final var member1 = new Member("test1", "test1.png", "1");
            final var member2 = new Member("test2", "test2.png", "2");
            final var member3 = new Member("test3", "test3.png", "3");
            final var members = List.of(member1, member2, member3);
            복수_유저_추가_요청(members);

            final var product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
            final var productId = 상품_추가_요청(product);

            final var review1 = new Review(member1, product, "review1.jpg", 2L, "이 김밥은 재밌습니다", true, 5L);
            final var review2 = new Review(member2, product, "review2.jpg", 4L, "역삼역", true, 351L);
            final var review3 = new Review(member3, product, "review3.jpg", 3L, "ㅇㅇ", false, 130L);
            final var reviews = List.of(review1, review2, review3);
            복수_리뷰_추가(reviews);

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
            final var category = new Category("간편식사", CategoryType.FOOD);
            카테고리_추가_요청(category);

            final var member1 = new Member("test1", "test1.png", "1");
            final var member2 = new Member("test2", "test2.png", "2");
            final var member3 = new Member("test3", "test3.png", "3");
            final var member4 = new Member("test4", "test4.png", "4");
            final var members = List.of(member1, member2, member3, member4);
            복수_유저_추가_요청(members);

            final var product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
            final var productId = 상품_추가_요청(product);

            final var review1 = new Review(member1, product, "review1.jpg", 3L, "이 김밥은 재밌습니다", true, 5L);
            final var review2 = new Review(member2, product, "review2.jpg", 3L, "역삼역", true, 351L);
            final var review3 = new Review(member3, product, "review3.jpg", 3L, "토마토", false, 130L);
            final var review4 = new Review(member4, product, "review4.jpg", 3L, "기러기", false, 130L);
            final var reviews = List.of(review1, review2, review3, review4);
            복수_리뷰_추가(reviews);

            final var sortingReviews = List.of(review4, review3, review2, review1);
            final var page = new SortingReviewsPageDto(4L, 1L, true, true, 0L, 10L);
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 정렬된_리뷰_목록_조회_요청(loginCookie, productId, "rating,desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            정렬된_리뷰_목록_조회_결과를_검증한다(response, sortingReviews, page, member1);
        }
    }

    @Test
    void 리뷰_랭킹을_조회하다() {
        // given
        final var category = new Category("간편식사", CategoryType.FOOD);
        카테고리_추가_요청(category);

        final var member1 = new Member("test1", "test1.png", "1");
        final var member2 = new Member("test2", "test2.png", "2");
        final var member3 = new Member("test3", "test3.png", "3");
        final var members = List.of(member1, member2, member3);
        복수_유저_추가_요청(members);

        final var product1 = new Product("김밥", 1000L, "image.png", "김밥", category);
        final var product2 = new Product("물", 500L, "water.jpg", "물", category);
        final var products = List.of(product1, product2);
        복수_상품_추가_요청(products);

        final var review1 = new Review(member1, product1, "review1.jpg", 3L, "이 김밥은 재밌습니다", true, 5L);
        final var review2 = new Review(member2, product1, "review2.jpg", 4L, "역삼역", true, 351L);
        final var review3 = new Review(member3, product1, "review3.jpg", 3L, "ㅇㅇ", false, 130L);
        final var review4 = new Review(member2, product2, "review4.jpg", 5L, "ㅁㅜㄹ", true, 247L);
        final var review5 = new Review(member3, product2, "review5.jpg", 1L, "ㄴㄴ", false, 83L);
        final var reviews = List.of(review1, review2, review3, review4, review5);
        복수_리뷰_추가(reviews);

        final var rankingReviews = List.of(review2, review4, review3);

        // when
        final var response = 리뷰_랭킹_조회_요청();

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        리뷰_랭킹_조회_결과를_검증한다(response, rankingReviews);
    }

    private void 리뷰_좋아요_결과를_검증한다(final ReviewFavorite result, final Long memberId, final Long reviewId) {
        assertThat(result.getId()).isNotNull();
        assertThat(result.getReview().getId()).isEqualTo(reviewId);
        assertThat(result.getMember().getId()).isEqualTo(memberId);
    }

    private List<Long> 태그_추가_요청() {
        final var testTag1 = tagRepository.save(new Tag("testTag1", TagType.ETC));
        final var testTag2 = tagRepository.save(new Tag("testTag2", TagType.ETC));
        return List.of(testTag1.getId(), testTag2.getId());
    }

    private Long 상품_추가_요청(final Product product) {
        return productRepository.save(product).getId();
    }

    private void 복수_상품_추가_요청(final List<Product> products) {
        productRepository.saveAll(products);
    }

    private Long 유저_추가_요청(final Member member) {
        return memberRepository.save(member).getId();
    }

    private void 복수_유저_추가_요청(final List<Member> members) {
        memberRepository.saveAll(members);
    }

    private void 카테고리_추가_요청(final Category category) {
        categoryRepository.save(category);
    }

    private void 복수_리뷰_추가(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    private void 정렬된_리뷰_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews,
                                       final SortingReviewsPageDto pageDto, final Member member) {
        페이지를_검증한다(response, pageDto);
        리뷰_목록을_검증한다(response, reviews, member);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final SortingReviewsPageDto expected) {
        final var actual = response.jsonPath().getObject("page", SortingReviewsPageDto.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private void 리뷰_목록을_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews,
                             final Member member) {
        final var expected = reviews.stream()
                .map(review -> SortingReviewDto.toDto(review, member))
                .collect(Collectors.toList());
        final var actual = response.jsonPath().getList("reviews", SortingReviewDto.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private void 리뷰_랭킹_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Review> reviews) {
        final var expected = reviews.stream()
                .map(RankingReviewDto::toDto)
                .collect(Collectors.toList());
        final var actual = response.jsonPath()
                .getList("reviews", RankingReviewDto.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
