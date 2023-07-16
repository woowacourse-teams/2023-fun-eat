package com.funeat.acceptance.review;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Gender;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class ReviewAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewFavoriteRepository reviewFavoriteRepository;

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

    private ExtractableResponse<Response> 리뷰_추가_요청(final Long productId, final MultiPartSpecification image,
                                                   final ReviewCreateRequest request) {
        return given()
                .multiPart(image)
                .multiPart("reviewRequest", request, "application/json")
                .when()
                .post("/api/products/{productId}/reviews", productId)
                .then()
                .extract();
    }

    private ExtractableResponse<Response> 리뷰_좋아요_요청(final Long productId, final Long reviewId,
                                                    final ReviewFavoriteRequest request) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .patch("/api/products/{productId}/reviews/{reviewId}", productId, reviewId)
                .then()
                .extract();
    }

    private MultiPartSpecification 리뷰_사진_명세_요청() {
        return new MultiPartSpecBuilder("image".getBytes())
                .fileName("testImage.png")
                .controlName("image")
                .mimeType("image/png")
                .build();
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
}
