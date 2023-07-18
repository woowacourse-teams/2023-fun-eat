package com.funeat.acceptance.review;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_사진_명세_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_추가_요청;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Gender;
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
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {
        reviewTagRepository.deleteAll();
        reviewFavoriteRepository.deleteAll();
        reviewRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    void 리뷰를_작성한다() {
        // given
        final Long savedMemberId = 멤버_추가_요청();
        final Long savedProductId = 상품_추가_요청();
        final List<Long> savedTagIds = 태그_추가_요청();
        final MultiPartSpecification image = 리뷰_사진_명세_요청();

        final var request = new ReviewCreateRequest(4.5, savedTagIds, "test content", true, savedMemberId);

        // when
        final var response = 리뷰_추가_요청(savedProductId, image, request);

        // then
        STATUS_CODE를_검증한다(response, 정상_생성);
    }

    @Test
    void 리뷰에_좋아요를_할_수_있다() {
        // given
        final Long savedMemberId = 멤버_추가_요청();
        final Long savedProductId = 상품_추가_요청();
        final List<Long> savedTagIds = 태그_추가_요청();
        final MultiPartSpecification image = 리뷰_사진_명세_요청();
        final var reviewRequest = new ReviewCreateRequest(4.5, savedTagIds, "test content", true, savedMemberId);
        final var favoriteRequest = new ReviewFavoriteRequest(true, savedMemberId);

        리뷰_추가_요청(savedProductId, image, reviewRequest);
        final var savedReviewId = reviewRepository.findAll().get(0).getId();

        // when
        final var response = 리뷰_좋아요_요청(savedProductId, savedReviewId, favoriteRequest);
        final var result = reviewFavoriteRepository.findAll().get(0);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
        리뷰_좋아요_결과를_검증한다(result, savedMemberId, savedReviewId);
        assertThat(result.getChecked()).isTrue();
    }

    @Test
    void 리뷰에_좋아요를_취소할_수_있다() {
        // given
        final Long savedMemberId = 멤버_추가_요청();
        final Long savedProductId = 상품_추가_요청();
        final List<Long> savedTagIds = 태그_추가_요청();
        final MultiPartSpecification image = 리뷰_사진_명세_요청();
        final var reviewRequest = new ReviewCreateRequest(4.5, savedTagIds, "test content", true, savedMemberId);
        final var favoriteRequest = new ReviewFavoriteRequest(true, savedMemberId);
        final var favoriteCancelRequest = new ReviewFavoriteRequest(false, savedMemberId);

        리뷰_추가_요청(savedProductId, image, reviewRequest);
        final var savedReview = reviewRepository.findAll().get(0);
        리뷰_좋아요_요청(savedProductId, savedReview.getId(), favoriteRequest);

        // when
        final var response = 리뷰_좋아요_요청(savedProductId, savedReview.getId(), favoriteCancelRequest);
        final var result = reviewFavoriteRepository.findAll().get(0);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
        리뷰_좋아요_결과를_검증한다(result, savedMemberId, savedReview.getId());
        assertThat(result.getChecked()).isFalse();
    }

    private void 리뷰_좋아요_결과를_검증한다(final ReviewFavorite result, final Long memberId, final Long reviewId) {
        assertThat(result.getId()).isNotNull();
        assertThat(result.getReview().getId()).isEqualTo(reviewId);
        assertThat(result.getMember().getId()).isEqualTo(memberId);
    }

    private List<Long> 태그_추가_요청() {
        final Tag testTag1 = tagRepository.save(new Tag("testTag1"));
        final Tag testTag2 = tagRepository.save(new Tag("testTag2"));
        return List.of(testTag1.getId(), testTag2.getId());
    }

    private Long 상품_추가_요청() {
        final Product testProduct = productRepository.save(new Product("testName", 1000L, "test.png", "test", null));

        return testProduct.getId();
    }

    private Long 멤버_추가_요청() {
        final Member testMember = memberRepository.save(
                new Member("test", "image.png", 27, Gender.FEMALE, "01036551086"));

        return testMember.getId();
    }

    @Test
    void 좋아요_기준_내림차순된_리뷰_목록을_조회한다() {
        // given
        final var category = new Category("간편식사", CategoryType.FOOD);
        카테고리_추가_요청(category);

        final var member1 = new Member("test1", "test1.png", 20, Gender.MALE, "010-1234-1234");
        final var member2 = new Member("test2", "test2.png", 41, Gender.FEMALE, "010-1357-2468");
        final var member3 = new Member("test3", "test3.png", 9, Gender.MALE, "010-9876-4321");
        final var members = List.of(member1, member2, member3);
        복수_유저_추가_요청(members);

        final var product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
        final var productId = 상품_추가_요청(product);

        final var review1 = new Review(member1, product, "review1.jpg", 3.0, "이 김밥은 재밌습니다", true, 5L);
        final var review2 = new Review(member2, product, "review2.jpg", 4.5, "역삼역", true, 351L);
        final var review3 = new Review(member3, product, "review3.jpg", 3.5, "ㅇㅇ", false, 130L);
        final var reviews = List.of(review1, review2, review3);
        복수_리뷰_추가(reviews);

        final var sortingReviews = List.of(review2, review3, review1);

        // when
        final var response = 좋아요_기준_리뷰_목록_조회_요청(productId, "favoriteCount,desc", 0);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        좋아요_기준_리뷰_목록_조회_결과를_검증한다(response, sortingReviews);
    }

    @Test
    void 리뷰_랭킹을_조회하다() {
        // given
        final var category = new Category("간편식사", CategoryType.FOOD);
        카테고리_추가_요청(category);

        final var member1 = new Member("test1", "test1.png", 20, Gender.MALE, "010-1234-1234");
        final var member2 = new Member("test2", "test2.png", 41, Gender.FEMALE, "010-1357-2468");
        final var member3 = new Member("test3", "test3.png", 9, Gender.MALE, "010-9876-4321");
        final var members = List.of(member1, member2, member3);
        복수_유저_추가_요청(members);

        final var product1 = new Product("김밥", 1000L, "image.png", "김밥", category);
        final var product2 = new Product("물", 500L, "water.jpg", "물", category);
        final var products = List.of(product1, product2);
        복수_상품_추가_요청(products);

        final var review1 = new Review(member1, product1, "review1.jpg", 3.0, "이 김밥은 재밌습니다", true, 5L);
        final var review2 = new Review(member2, product1, "review2.jpg", 4.5, "역삼역", true, 351L);
        final var review3 = new Review(member3, product1, "review3.jpg", 3.5, "ㅇㅇ", false, 130L);
        final var review4 = new Review(member2, product2, "review4.jpg", 5.0, "ㅁㅜㄹ", true, 247L);
        final var review5 = new Review(member3, product2, "review5.jpg", 1.5, "ㄴㄴ", false, 83L);
        final var reviews = List.of(review1, review2, review3, review4, review5);
        복수_리뷰_추가(reviews);

        final var rankingReviews = List.of(review2, review4, review3);

        // when
        final var response = 리뷰_랭킹_조회_요청();

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        리뷰_랭킹_조회_결과를_검증한다(response, rankingReviews);
    }

    private void 카테고리_추가_요청(final Category category) {
        categoryRepository.save(category);
    }

    private void 복수_유저_추가_요청(final List<Member> members) {
        memberRepository.saveAll(members);
    }

    private Long 상품_추가_요청(final Product product) {
        return productRepository.save(product).getId();
    }

    private void 복수_상품_추가_요청(final List<Product> products) {
        productRepository.saveAll(products);
    }

    private void 복수_리뷰_추가(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    private ExtractableResponse<Response> 좋아요_기준_리뷰_목록_조회_요청(final Long productId,
                                                             final String sort,
                                                             final Integer page) {
        return given()
                .queryParam("sort", sort)
                .queryParam("page", page)
                .when()
                .get("/api/products/{product_id}/reviews", productId)
                .then()
                .extract();
    }

    private ExtractableResponse<Response> 리뷰_랭킹_조회_요청() {
        return given()
                .when()
                .get("/api/ranks/reviews")
                .then()
                .extract();
    }

    private void 좋아요_기준_리뷰_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response,
                                          final List<Review> reviews) {
        페이지를_검증한다(response);
        리뷰_목록을_검증한다(response, reviews);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response) {
        final var expected = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);
        final var actual = response.jsonPath().getObject("page", SortingReviewsPageDto.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private void 리뷰_목록을_검증한다(final ExtractableResponse<Response> response,
                             final List<Review> reviews) {
        final List<SortingReviewDto> expected = reviews.stream()
                .map(SortingReviewDto::toDto)
                .collect(Collectors.toList());
        final List<SortingReviewDto> actual = response.jsonPath().getList("reviews", SortingReviewDto.class);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    private void 리뷰_랭킹_조회_결과를_검증한다(final ExtractableResponse<Response> response,
                                   final List<Review> reviews) {
        final var expected = reviews.stream()
                .map(RankingReviewDto::toDto)
                .collect(Collectors.toList());
        final var actual = response.jsonPath()
                .getList("reviews", RankingReviewDto.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
