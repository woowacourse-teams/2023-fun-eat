package com.funeat.acceptance.product;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.product.ProductSteps.CU;
import static com.funeat.acceptance.product.ProductSteps.간편식사;
import static com.funeat.acceptance.product.ProductSteps.공통_상품_카테고리_목록_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.과자류;
import static com.funeat.acceptance.product.ProductSteps.상품_상세_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.즉석조리;
import static com.funeat.acceptance.product.ProductSteps.카테고리별_상품_목록_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.CategoryResponse;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductsInCategoryPageDto;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ProductAcceptanceTest extends AcceptanceTest {

    public static final int PAGE_SIZE = 10;

    @Test
    void 카테고리별_상품_목록을_가격낮은순으로_조회한다() {
        // given
        final Long categoryId = 카테고리_추가_요청(간편식사);
        final Product product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
        final Product product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 간편식사);
        final Product product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 간편식사);
        final Product product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 간편식사);
        final Product product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 간편식사);
        final Product product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", 간편식사);
        final Product product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", 간편식사);
        final Product product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", 간편식사);
        final Product product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", 간편식사);
        final Product product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", 간편식사);
        final Product product11 = new Product("삼각김밥11", 300L, "image.png", "맛있는 삼각김밥11", 간편식사);
        final List<Product> products = List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11);
        복수_상품_추가_요청(products);

        // when
        final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "asc", 0);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        페이지를_검증한다(response, (long) products.size(), 0L);
        카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(product11, product8, product1, product4, product3, product6, product7, product2, product5, product10));
    }

    @Test
    void 카테고리별_상품_목록을_가격높은순으로_조회한다() {
        // given
        final Long categoryId = 카테고리_추가_요청(간편식사);
        final Product product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
        final Product product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 간편식사);
        final Product product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 간편식사);
        final Product product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 간편식사);
        final Product product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 간편식사);
        final Product product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", 간편식사);
        final Product product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", 간편식사);
        final Product product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", 간편식사);
        final Product product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", 간편식사);
        final Product product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", 간편식사);
        final Product product11 = new Product("삼각김밥11", 300L, "image.png", "맛있는 삼각김밥11", 간편식사);
        final List<Product> products = List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11);
        복수_상품_추가_요청(products);


        // when
        final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "desc", 0);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        페이지를_검증한다(response, (long) products.size(), 0L);
        카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(product9, product10, product5, product2, product7, product6, product3, product4, product1, product8));
    }

    @Test
    void 상품_상세_정보를_조회한다() {
        // given
        카테고리_추가_요청(간편식사);
        final Product product = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
        final Long productId = 상품_추가_요청(product);

        // when
        final var response = 상품_상세_조회_요청(productId);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        상품_상세_정보_조회_결과를_검증한다(response, product);
    }

    @Test
    void 공통_상품_카테고리의_목록을_조회한다() {
        // given
        카테고리_추가_요청(간편식사);
        카테고리_추가_요청(즉석조리);
        카테고리_추가_요청(과자류);
        카테고리_추가_요청(CU);

        // when
        final var response = 공통_상품_카테고리_목록_조회_요청();

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        공통_상품_카테고리_목록_조회_결과를_검증한다(response, List.of(간편식사, 즉석조리, 과자류));
    }

    private Long 카테고리_추가_요청(final Category category) {
        return categoryRepository.save(category).getId();
    }

    private Long 상품_추가_요청(final Product product) {
        return productRepository.save(product).getId();
    }

    private void 복수_상품_추가_요청(final List<Product> products) {
        productRepository.saveAll(products);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, Long dataSize, Long page) {
        long totalPages = (long) Math.ceil((double) dataSize / PAGE_SIZE);
        ProductsInCategoryPageDto expected = new ProductsInCategoryPageDto(dataSize, totalPages,
                page == 0, page == totalPages - 1, page, (long) PAGE_SIZE);
        ProductsInCategoryPageDto actual = response.jsonPath().getObject("page", ProductsInCategoryPageDto.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private void 카테고리별_상품_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Product> products) {
        final List<ProductInCategoryDto> expected = new ArrayList<>();
        for (Product product : products) {
            expected.add(ProductInCategoryDto.toDto(product, 0L));
        }

        final List<ProductInCategoryDto> actual = response.jsonPath().getList("products", ProductInCategoryDto.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "reviewCount")
                .isEqualTo(expected);
    }

    private void 상품_상세_정보_조회_결과를_검증한다(final ExtractableResponse<Response> response, final Product product) {
        final ProductResponse expected = ProductResponse.toResponse(product, Collections.emptyList());

        final ProductResponse actual = response.as(ProductResponse.class);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "averageRating")
                .isEqualTo(expected);
    }

    private void 공통_상품_카테고리_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response,
                                           final List<Category> categories) {
        final List<CategoryResponse> expected = new ArrayList<>();
        for (Category category : categories) {
            expected.add(CategoryResponse.toResponse(category));
        }

        final List<CategoryResponse> actualResponses = response.as(new TypeRef<>() {
        });
        assertThat(actualResponses).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
