package com.funeat.member.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewFavoriteRepositoryTest extends RepositoryTest {

    @Test
    void 멤버와_리뷰로_리뷰_좋아요를_조회할_수_있다() {
        // given
        final var member = new Member("test", "image.png", "1");
        final var product = new Product("testName", 1000L, "test.png", "test", null);
        final var image = 리뷰_사진_명세_요청();
        final var review = new Review(member, product, image.getFileName(), 4L, "content", true);
        final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);
        단일_멤버_저장(member);
        단일_상품_저장(product);
        단일_리뷰_저장(review);
        단일_리뷰_좋아요_저장(reviewFavorite);

        // when
        final var result = reviewFavoriteRepository.findByMemberAndReview(member, review).get();

        // then
        final var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);

        assertThat(result).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .comparingOnlyFields("member", "review", "checked")
                .isEqualTo(expected);
    }

    private Long 단일_멤버_저장(final Member member) {
        return memberRepository.save(member).getId();
    }

    private Long 단일_상품_저장(final Product product) {
        return productRepository.save(product).getId();
    }

    private Review 단일_리뷰_저장(final Review review) {
        return reviewRepository.save(review);
    }

    private Long 단일_리뷰_좋아요_저장(final ReviewFavorite reviewFavorite) {
        return reviewFavoriteRepository.save(reviewFavorite).getId();
    }

    private MultiPartSpecification 리뷰_사진_명세_요청() {
        return new MultiPartSpecBuilder("image".getBytes())
                .fileName("testImage.png")
                .controlName("image")
                .mimeType("image/png")
                .build();
    }
}
