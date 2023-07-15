package com.funeat.acceptance.review;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Gender;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class ReviewAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    @Test
    void 좋아요_기준_내림차순된_리뷰_목록을_조회한다() {
        // given
        final Category category = new Category("간편식사", CategoryType.FOOD);
        카테고리_추가_요청(category);

        final Member member1 = new Member("test1", "test1.png", 20, Gender.MALE, "010-1234-1234");
        final Member member2 = new Member("test2", "test2.png", 41, Gender.FEMALE, "010-1357-2468");
        final Member member3 = new Member("test3", "test3.png", 9, Gender.MALE, "010-9876-4321");
        final List<Member> members = List.of(member1, member2, member3);
        복수_유저_추가_요청(members);

        final Product product = new Product("삼각김밥1", 1000L, "image.png", "김밥", category);
        final Long productId = 상품_추가_요청(product);

        final Review review1 = new Review(member1, product, "review1.jpg", 3.0, "이 김밥은 재밌습니다", true, 5L);
        final Review review2 = new Review(member2, product, "review2.jpg", 4.5, "역삼역", true, 351L);
        final Review review3 = new Review(member3, product, "review3.jpg", 3.5, "ㅇㅇ", false, 130L);
        final List<Review> reviews = List.of(review1, review2, review3);
        복수_리뷰_추가(reviews);

        final List<Review> sortingReviews = List.of(review2, review3, review1);

        // when
        final var response = 좋아요_기준_리뷰_목록_조회_요청(productId, "favorite", 0);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        좋아요_기준_리뷰_목록_조회_결과를_검증한다(response, sortingReviews);
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

    private void 복수_리뷰_추가(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    private ExtractableResponse<Response> 좋아요_기준_리뷰_목록_조회_요청(final Long productId,
                                                             final String sortOrderType,
                                                             final Integer page) {
        return given()
                .queryParam("option", sortOrderType)
                .queryParam("page", page)
                .when()
                .get("/api/products/{product_id}/reviews", productId)
                .then()
                .extract();
    }

    private void 좋아요_기준_리뷰_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response,
                                          final List<Review> reviews) {
        페이지를_검증한다(response);
        리뷰_목록을_검증한다(response, reviews);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response) {
        final SortingReviewsPageDto expectedPage = new SortingReviewsPageDto(3L, 1L, true, true, 0L, 10L);
        final SortingReviewsPageDto actualPage = response.jsonPath().getObject("page", SortingReviewsPageDto.class);
        assertThat(actualPage).usingRecursiveComparison().isEqualTo(expectedPage);
    }

    private void 리뷰_목록을_검증한다(final ExtractableResponse<Response> response,
                             final List<Review> reviews) {
        final List<SortingReviewDto> expectedReviews = reviews.stream()
                .map(SortingReviewDto::toDto)
                .collect(Collectors.toList());
        final List<SortingReviewDto> actualReviews = response.jsonPath().getList("reviews", SortingReviewDto.class);
        assertThat(actualReviews).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedReviews);
    }
}
