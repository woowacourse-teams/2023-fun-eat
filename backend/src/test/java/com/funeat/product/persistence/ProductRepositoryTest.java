package com.funeat.product.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.Member;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductReviewCountDto;
import com.funeat.review.domain.Review;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SuppressWarnings("NonAsciiCharacters")
class ProductRepositoryTest extends RepositoryTest {

    @Nested
    class findByAllCategory_테스트 {

        @Test
        void 카테고리별_상품을_평점이_높은_순으로_정렬한다() {
            // given
            final var category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, category);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.5, category);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 4.0, category);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 3.0, category);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 4.5, category);
            final var products = List.of(product1, product2, product3, product4, product5);
            복수_상품_저장(products);

            // when
            final var pageRequest = PageRequest.of(0, 3, Sort.by("averageRating").descending());
            final var actual = productRepository.findAllByCategory(category, pageRequest).getContent();

            // then
            final var expected = List.of(ProductInCategoryDto.toDto(product1, 0L),
                    ProductInCategoryDto.toDto(product5, 0L),
                    ProductInCategoryDto.toDto(product3, 0L));
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 카테고리별_상품을_평점이_낮은_순으로_정렬한다() {
            // given
            final var category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, category);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.5, category);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 4.0, category);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 3.0, category);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 4.5, category);
            final var products = List.of(product1, product2, product3, product4, product5);
            복수_상품_저장(products);

            // when
            final var pageRequest = PageRequest.of(0, 3, Sort.by("averageRating").ascending());
            final var actual = productRepository.findAllByCategory(category, pageRequest).getContent();

            // then
            final var expected = List.of(ProductInCategoryDto.toDto(product4, 0L),
                    ProductInCategoryDto.toDto(product2, 0L),
                    ProductInCategoryDto.toDto(product3, 0L));
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 카테고리별_상품을_가격이_높은_순으로_정렬한다() {
            // given
            final var category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", category);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", category);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", category);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", category);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", category);
            final var product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", category);
            final var product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", category);
            final var product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", category);
            final var product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", category);
            final var product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", category);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7, product8,
                    product9, product10);
            복수_상품_저장(products);

            // when
            final var pageRequest = PageRequest.of(0, 3, Sort.by("price").descending());
            final var actual = productRepository.findAllByCategory(category, pageRequest).getContent();

            // then
            final var expected = List.of(ProductInCategoryDto.toDto(product9, 0L),
                    ProductInCategoryDto.toDto(product10, 0L), ProductInCategoryDto.toDto(product5, 0L));
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 카테고리별_상품을_가격이_낮은_순으로_정렬한다() {
            // given
            final var category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", category);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", category);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", category);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", category);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", category);
            final var product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", category);
            final var product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", category);
            final var product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", category);
            final var product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", category);
            final var product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", category);
            final List<Product> products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10);
            복수_상품_저장(products);

            // when
            final var pageRequest = PageRequest.of(0, 3, Sort.by("price").ascending());
            final var actual = productRepository.findAllByCategory(category, pageRequest).getContent();

            // then
            final var expected = List.of(ProductInCategoryDto.toDto(product8, 0L),
                    ProductInCategoryDto.toDto(product1, 0L),
                    ProductInCategoryDto.toDto(product4, 0L));
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllByCategoryOrderByReviewCountDesc_테스트 {

        @Test
        void 카테고리별_상품을_리뷰수가_많은_순으로_정렬한다() {
            // given
            final var category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", category);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", category);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", category);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", category);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", category);
            final var products = List.of(product1, product2, product3, product4, product5);
            복수_상품_저장(products);

            final var member = new Member("test", "image.png", "1");
            단일_멤버_저장(member);

            final var review1_1 = new Review(member, product1, "review.png", 5L, "이 삼각김밥은 최고!!", true);
            final var review1_2 = new Review(member, product1, "review.png", 4L, "이 삼각김밥은 좀 맛있다", true);
            final var review1_3 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
            final var review1_4 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
            final var review2_1 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review2_2 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review3_1 = new Review(member, product3, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review4_1 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var review4_2 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var review4_3 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var reviews = List.of(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2, review3_1,
                    review4_1, review4_2, review4_3);
            복수_리뷰_저장(reviews);

            // when
            final var pageRequest = PageRequest.of(0, 3);
            final var actual = productRepository.findAllByCategoryOrderByReviewCountDesc(category, pageRequest)
                    .getContent();

            // then
            final var expected = List.of(ProductInCategoryDto.toDto(product1, 4L),
                    ProductInCategoryDto.toDto(product4, 3L),
                    ProductInCategoryDto.toDto(product2, 2L));
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllByAverageRatingGreaterThan3_테스트 {

        @Test
        void 평점이_3보다_큰_모든_상품들과_리뷰_수를_조회한다() {
            // given
            final var category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 3.75, category);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 1.0, category);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 5.0, category);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 2.0, category);
            final var products = List.of(product1, product2, product3, product4);
            복수_상품_저장(products);

            final var member = new Member("test", "image.png", "1");
            단일_멤버_저장(member);

            final var review1_1 = new Review(member, product1, "review.png", 5L, "이 삼각김밥은 최고!!", true);
            final var review1_2 = new Review(member, product1, "review.png", 4L, "이 삼각김밥은 좀 맛있다", true);
            final var review1_3 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
            final var review1_4 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
            final var review2_1 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review2_2 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review3_1 = new Review(member, product3, "review.png", 5L, "이 삼각김밥은 굿굿", false);
            final var review4_1 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var review4_2 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var review4_3 = new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var reviews = List.of(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2, review3_1,
                    review4_1, review4_2, review4_3);
            복수_리뷰_저장(reviews);

            // when
            final var actual = productRepository.findAllByAverageRatingGreaterThan3();

            // then
            final var expected = List.of(new ProductReviewCountDto(product1, 4L),
                    new ProductReviewCountDto(product3, 1L));
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    private void 복수_상품_저장(final List<Product> products) {
        productRepository.saveAll(products);
    }

    private void 복수_리뷰_저장(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    private Long 단일_멤버_저장(final Member member) {
        return memberRepository.save(member).getId();
    }
}
