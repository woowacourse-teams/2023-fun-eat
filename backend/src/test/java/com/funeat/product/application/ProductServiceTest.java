package com.funeat.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.product.dto.RankingProductsResponse;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.persistence.ReviewRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 전체_상품_중_랭킹이_높은_상위_3개_상품을_구할_수_있다() {
        // given
        final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 3.5, null);
        final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 4.0, null);
        final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 5.0, null);
        final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 2.0, null);
        productRepository.saveAll(List.of(product1, product2, product3, product4));

        final var member = memberRepository.save(new Member("test", "image.png", "1"));
        final var review1_1 = new Review(member, product1, "review.png", 5L, "이 삼각김밥은 최고!!", true);
        final var review1_2 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
        final var review1_3 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
        final var review1_4 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
        final var review2_1 = new Review(member, product2, "review.png", 4L, "이 삼각김밥은 맛있다", false);
        final var review2_2 = new Review(member, product2, "review.png", 4L, "이 삼각김밥은 맛있다", false);
        final var review3_1 = new Review(member, product3, "review.png", 5L, "이 삼각김밥은 굿굿", false);
        final var review4_1 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
        final var review4_2 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
        final var review4_3 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
        reviewRepository.saveAll(
                List.of(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2, review3_1, review4_1,
                        review4_2, review4_3)
        );

        // when
        final var actual = productService.getTop3Products();

        // then
        final var expected = RankingProductsResponse.toResponse(
                List.of(RankingProductDto.toDto(product3), RankingProductDto.toDto(product2),
                        RankingProductDto.toDto(product1))
        );
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
