package com.funeat.acceptance.recipe;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.product.ProductSteps.간편식사;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_추가_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.여러_사진_요청;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.acceptance.common.LoginSteps;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.recipe.dto.RecipeCreateRequest;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RecipeAcceptanceTest extends AcceptanceTest {

    @Test
    void 레시피를_작성한다() {
        // given
        카테고리_추가_요청(간편식사);
        final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
        final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 간편식사);
        final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 간편식사);
        복수_상품_추가_요청(List.of(product1, product2, product3));
        final var loginCookie = LoginSteps.로그인_쿠키를_얻는다();

        // when
        final var request = new RecipeCreateRequest("제일로 맛있는 레시피",
                List.of(product1.getId(), product2.getId(), product3.getId()),
                "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");
        final List<MultiPartSpecification> images = 여러_사진_요청(3);
        final var response = 레시피_추가_요청(request, images, loginCookie);

        // then
        STATUS_CODE를_검증한다(response, 정상_생성);
    }

    private Long 카테고리_추가_요청(final Category category) {
        return categoryRepository.save(category).getId();
    }

    private void 복수_상품_추가_요청(final List<Product> products) {
        productRepository.saveAll(products);
    }
}
