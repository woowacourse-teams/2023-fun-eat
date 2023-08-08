package com.funeat.member.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewFavoriteRepositoryTest extends RepositoryTest {

    @Nested
    class findByMemberAndReview_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 멤버와_리뷰로_리뷰_좋아요를_조회할_수_있다() {
                // given
                final var member = new Member("test", "image.png", "1");
                단일_멤버_저장(member);

                final var product = new Product("testName", 1000L, "test.png", "test", null);
                단일_상품_저장(product);

                final var image = 리뷰_사진_명세_요청();
                final var review = new Review(member, product, image.getFileName(), 4L, "content", true);
                단일_리뷰_저장(review);

                final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);
                단일_리뷰_좋아요_저장(reviewFavorite);

                final var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);

                // when
                final var actual = reviewFavoriteRepository.findByMemberAndReview(member, review).get();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .ignoringExpectedNullFields()
                        .isEqualTo(expected);
            }
        }

        @Nested
        class 실패_테스트 {

            @Test
            void 잘못된_멤버로_좋아요를_조회하면_에러가_발생한다() {
                // given
                final var member = new Member("test", "image.png", "1");
                단일_멤버_저장(member);

                final var product = new Product("testName", 1000L, "test.png", "test", null);
                단일_상품_저장(product);

                final var image = 리뷰_사진_명세_요청();
                final var review = new Review(member, product, image.getFileName(), 4L, "content", true);
                단일_리뷰_저장(review);

                final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);
                단일_리뷰_좋아요_저장(reviewFavorite);

                final var wrongMember = new Member("wrong", "wrong.png", "2");
                단일_멤버_저장(wrongMember);

                // when, then
                assertThatThrownBy(() -> reviewFavoriteRepository.findByMemberAndReview(wrongMember, review).get())
                        .isInstanceOf(NoSuchElementException.class);
            }

            @Test
            void 잘못된_리뷰로_좋아요를_조회하면_에러가_발생한다() {
                // given
                final var member = new Member("test", "image.png", "1");
                단일_멤버_저장(member);
                final var anotherMember = new Member("another", "another.png", "2");
                단일_멤버_저장(anotherMember);

                final var product = new Product("testName", 1000L, "test.png", "test", null);
                단일_상품_저장(product);

                final var image = 리뷰_사진_명세_요청();
                final var review = new Review(member, product, image.getFileName(), 4L, "content", true);
                단일_리뷰_저장(review);

                final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);
                단일_리뷰_좋아요_저장(reviewFavorite);

                final var wrongReview = new Review(anotherMember, product, image.getFileName(), 5L, "content", false);
                단일_리뷰_저장(wrongReview);

                // when, then
                assertThatThrownBy(() -> reviewFavoriteRepository.findByMemberAndReview(member, wrongReview).get())
                        .isInstanceOf(NoSuchElementException.class);
            }
        }
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
