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
        final var member = 멤버_추가_요청();
        final var product = 상품_추가_요청();
        final var review = 리뷰_추가_요청(member, product);
        final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);
        reviewFavoriteRepository.save(reviewFavorite);

        // when
        final var result = reviewFavoriteRepository.findByMemberAndReview(member, review).get();

        // then
        final var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);

        assertThat(result).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .comparingOnlyFields("member", "review", "checked")
                .isEqualTo(expected);
    }

    private Member 멤버_추가_요청() {
        return memberRepository.save(new Member("test", "image.png", "1"));
    }

    private Product 상품_추가_요청() {
        return productRepository.save(new Product("testName", 1000L, "test.png", "test", null));
    }

    private Review 리뷰_추가_요청(final Member member, final Product product) {
        final var image = 리뷰_사진_명세_요청();
        return reviewRepository.save(new Review(member, product, image.getFileName(), 4L, "content", true));
    }

    private MultiPartSpecification 리뷰_사진_명세_요청() {
        return new MultiPartSpecBuilder("image".getBytes())
                .fileName("testImage.png")
                .controlName("image")
                .mimeType("image/png")
                .build();
    }
}
