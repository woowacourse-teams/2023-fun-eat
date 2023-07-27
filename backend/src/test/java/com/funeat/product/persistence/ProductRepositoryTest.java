package com.funeat.product.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.review.domain.Review;
import com.funeat.review.persistence.ReviewRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 카테고리별_상품을_평점이_높은_순으로_정렬한다() {
        // given
        final var category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
        final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, category);
        final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.5, category);
        final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 4.0, category);
        final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 3.0, category);
        final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 4.5, category);
        productRepository.saveAll(
                List.of(product1, product2, product3, product4, product5));

        // when
        final var pageRequest = PageRequest.of(0, 3, Sort.by("averageRating").descending());
        final var actual = productRepository.findAllByCategory(category, pageRequest).getContent();

        // then
        final var expected = List.of(ProductInCategoryDto.toDto(product1, 0L), ProductInCategoryDto.toDto(product5, 0L), ProductInCategoryDto.toDto(product3, 0L));
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
        productRepository.saveAll(
                List.of(product1, product2, product3, product4, product5));

        // when
        final var pageRequest = PageRequest.of(0, 3, Sort.by("averageRating").ascending());
        final var actual = productRepository.findAllByCategory(category, pageRequest).getContent();

        // then
        final var expected = List.of(ProductInCategoryDto.toDto(product4, 0L), ProductInCategoryDto.toDto(product2, 0L),
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
        productRepository.saveAll(
                List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10));

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
        productRepository.saveAll(
                List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10));

        // when
        final var pageRequest = PageRequest.of(0, 3, Sort.by("price").ascending());
        final var actual = productRepository.findAllByCategory(category, pageRequest).getContent();

        // then
        final var expected = List.of(ProductInCategoryDto.toDto(product8, 0L), ProductInCategoryDto.toDto(product1, 0L),
                ProductInCategoryDto.toDto(product4, 0L));
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 카테고리별_상품을_리뷰수가_많은_순으로_정렬한다() {
        // given
        final var category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
        final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", category);
        final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", category);
        final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", category);
        final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", category);
        final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", category);
        productRepository.saveAll(
                List.of(product1, product2, product3, product4, product5));

        final var member = memberRepository.save(new Member("test", "image.png"));

        reviewRepository.save(new Review(member, product1, "review.png", 5L, "이 삼각김밥은 최고!!", true));
        reviewRepository.save(new Review(member, product1, "review.png", 4L, "이 삼각김밥은 좀 맛있다", true));
        reviewRepository.save(new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true));
        reviewRepository.save(new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true));
        reviewRepository.save(new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false));
        reviewRepository.save(new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false));
        reviewRepository.save(new Review(member, product4, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false));
        reviewRepository.save(new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false));
        reviewRepository.save(new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false));
        reviewRepository.save(new Review(member, product3, "review.png", 1L, "이 삼각김밥은 맛없다", false));

        // when
        final var pageRequest = PageRequest.of(0, 3);
        final var actual = productRepository.findAllByCategoryOrderByReviewCountDesc(category, pageRequest)
                .getContent();

        // then
        final var expected = List.of(ProductInCategoryDto.toDto(product1, 4L), ProductInCategoryDto.toDto(product4, 3L),
                ProductInCategoryDto.toDto(product2, 2L));
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
