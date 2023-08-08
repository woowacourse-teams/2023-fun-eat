package com.funeat.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.ServiceTest;
import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.product.dto.RankingProductsResponse;
import com.funeat.review.domain.Review;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ProductServiceTest extends ServiceTest {

    @Nested
    class getTop3Products_테스트 {

        @Nested
        class 성공_테스트 {

            @Nested
            class 상품_개수에_대한_테스트 {

                @Test
                void 전체_상품이_하나도_없어도_반환값은_있어야한다() {
                    // given
                    final var expected = RankingProductsResponse.toResponse(List.of());

                    // when
                    final var actual = productService.getTop3Products();

                    // then
                    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
                }

                @Test
                void 전체_상품이_1개_이상_3개_미만이라도_상품이_나와야한다() {
                    // given
                    final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 3.5, null);
                    final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 4.0, null);
                    final var products = List.of(product1, product2);
                    복수_상품_추가(products);

                    final var member = new Member("test", "image.png", "1");
                    단일_멤버_추가(member);

                    final var review1_1 = new Review(member, product1, "review.png", 5L, "이 삼각김밥은 최고!!", true);
                    final var review1_2 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
                    final var review1_3 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
                    final var review1_4 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
                    final var review2_1 = new Review(member, product2, "review.png", 4L, "이 삼각김밥은 맛있다", false);
                    final var review2_2 = new Review(member, product2, "review.png", 4L, "이 삼각김밥은 맛있다", false);
                    final var reviews = List.of(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2);
                    복수_리뷰_저장(reviews);

                    final var rankingProductDto1 = RankingProductDto.toDto(product2);
                    final var rankingProductDto2 = RankingProductDto.toDto(product1);
                    final var rankingProductDtos = List.of(rankingProductDto1, rankingProductDto2);
                    final var expected = RankingProductsResponse.toResponse(rankingProductDtos);

                    // when
                    final var actual = productService.getTop3Products();

                    // then
                    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
                }

                @Test
                void 전체_상품_중_랭킹이_높은_상위_3개_상품을_구할_수_있다() {
                    // given
                    final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 3.5, null);
                    final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 4.0, null);
                    final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 5.0, null);
                    final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 2.0, null);
                    final var products = List.of(product1, product2, product3, product4);
                    복수_상품_추가(products);

                    final var member = new Member("test", "image.png", "1");
                    단일_멤버_추가(member);

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
                    final var reviews = List.of(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2,
                            review3_1,
                            review4_1, review4_2, review4_3);
                    복수_리뷰_저장(reviews);

                    final var rankingProductDto1 = RankingProductDto.toDto(product3);
                    final var rankingProductDto2 = RankingProductDto.toDto(product2);
                    final var rankingProductDto3 = RankingProductDto.toDto(product1);
                    final var rankingProductDtos = List.of(rankingProductDto1, rankingProductDto2, rankingProductDto3);
                    final var expected = RankingProductsResponse.toResponse(rankingProductDtos);

                    // when
                    final var actual = productService.getTop3Products();

                    // then
                    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
                }
            }

            @Nested
            class 상품_점수에_대한_테스트 {

                @Test
                void 모든_상품의_평점이_3점_미만이면_빈_배열을_반환한다() {
                    // given
                    final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 2.0, null);
                    final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 1.5, null);
                    final var products = List.of(product1, product2);
                    복수_상품_추가(products);

                    final var member = new Member("test", "image.png", "1");
                    단일_멤버_추가(member);

                    final var review1_1 = new Review(member, product1, "review.png", 1L, "이 삼각김밥은 최고일까요?", true);
                    final var review1_2 = new Review(member, product1, "review.png", 2L, "이 삼각김밥은 맛있을까요?", true);
                    final var review1_3 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있을까요?", true);
                    final var review2_1 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛있을까요?", false);
                    final var review2_2 = new Review(member, product2, "review.png", 2L, "이 삼각김밥은 맛있을까요?", false);
                    final var reviews = List.of(review1_1, review1_2, review1_3, review2_1, review2_2);
                    복수_리뷰_저장(reviews);

                    final var expected = RankingProductsResponse.toResponse(List.of());

                    // when
                    final var actual = productService.getTop3Products();

                    // then
                    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
                }

                @Test
                void 일부_상품이_평점_3점_이상이면_일부_상품만_반환한다() {
                    // given
                    final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 2.0, null);
                    final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 4.5, null);
                    final var products = List.of(product1, product2);
                    복수_상품_추가(products);

                    final var member = new Member("test", "image.png", "1");
                    단일_멤버_추가(member);

                    final var review1_1 = new Review(member, product1, "review.png", 1L, "이 삼각김밥은 최고일까요?", true);
                    final var review1_2 = new Review(member, product1, "review.png", 2L, "이 삼각김밥은 맛있을까요?", true);
                    final var review1_3 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있을까요?", true);
                    final var review2_1 = new Review(member, product2, "review.png", 4L, "이 삼각김밥은 맛있어요!", false);
                    final var review2_2 = new Review(member, product2, "review.png", 5L, "이 삼각김밥은 맛있어요!", false);
                    final var reviews = List.of(review1_1, review1_2, review1_3, review2_1, review2_2);
                    복수_리뷰_저장(reviews);

                    final var rankingProductDtos = List.of(RankingProductDto.toDto(product2));
                    final var expected = RankingProductsResponse.toResponse(rankingProductDtos);

                    // when
                    final var actual = productService.getTop3Products();

                    // then
                    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
                }
            }
        }

        @Nested
        class 실패_테스트 {
        }
    }

    private void 복수_상품_추가(final List<Product> products) {
        productRepository.saveAll(products);
    }

    private Long 단일_멤버_추가(final Member member) {
        return memberRepository.save(member).getId();
    }

    private void 복수_리뷰_저장(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }
}
