package com.funeat.product.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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

    private Category category;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;
    private Product product6;
    private Product product7;
    private Product product8;
    private Product product9;
    private Product product10;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));

        product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", category);
        product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", category);
        product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", category);
        product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", category);
        product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", category);
        product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", category);
        product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", category);
        product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", category);
        product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", category);
        product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", category);
    }

    @Test
    void 카테고리별_상품을_가격이_높은_순으로_정렬한다() {
        // given
        productRepository.saveAll(
                List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10));

        // when
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by("price").descending());
        List<Product> actual = productRepository.findAllByCategory(category, pageRequest).getContent();

        // then
        assertThat(actual).containsExactly(product9, product10, product5);
    }

    @Test
    void 카테고리별_상품을_가격이_낮은_순으로_정렬한다() {
        // given
        productRepository.saveAll(
                List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10));

        // when
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by("price").ascending());
        List<Product> actual = productRepository.findAllByCategory(category, pageRequest).getContent();

        // then
        assertThat(actual).containsExactly(product8, product1, product4);
    }
}
