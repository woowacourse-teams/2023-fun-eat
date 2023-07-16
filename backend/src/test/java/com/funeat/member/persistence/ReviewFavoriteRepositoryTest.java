package com.funeat.member.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.member.domain.Gender;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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
        final var reviewFavorite = new ReviewFavorite(true);
        reviewFavorite.updateByMemberAndReview(member, review);
        reviewFavoriteRepository.save(reviewFavorite);

        // then
        final var result = reviewFavoriteRepository.findByMemberAndReview(member, review).get();

        // when
        var expected = new ReviewFavorite(true);
        expected.updateByMemberAndReview(member, review);

        assertThat(result).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .comparingOnlyFields("member", "review", "checked")
                .isEqualTo(expected);
    }

    private Member 멤버_추가_요청() {
        return memberRepository.save(
                new Member("test", "image.png", 27, Gender.FEMALE, "01036551086"));
    }

    private Product 상품_추가_요청() {
        return productRepository.save(new Product("testName", 1000L, "test.png", "test", null));
    }

    private Review 리뷰_추가_요청(final Member member, final Product product) {
        final var image = 리뷰_사진_명세_요청();
        return reviewRepository.save(new Review(member, product, image.getFileName(), 4.5, "content", true));
    }

    private MultiPartSpecification 리뷰_사진_명세_요청() {
        return new MultiPartSpecBuilder("image".getBytes())
                .fileName("testImage.png")
                .controlName("image")
                .mimeType("image/png")
                .build();
    }
}
