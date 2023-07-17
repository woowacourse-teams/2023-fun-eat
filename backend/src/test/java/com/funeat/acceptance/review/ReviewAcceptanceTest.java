package com.funeat.acceptance.review;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static io.restassured.RestAssured.given;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Gender;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
    private ReviewTagRepository reviewTagRepository;

    @BeforeEach
    void init() {
        reviewTagRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
        tagRepository.deleteAll();
        reviewRepository.deleteAll();
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
}
