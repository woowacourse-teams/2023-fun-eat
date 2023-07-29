package com.funeat.member.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.persistence.ReviewRepository;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ReviewFavoriteRepositoryTest {

    @Autowired
    private ReviewFavoriteRepository reviewFavoriteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

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
        var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);

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
