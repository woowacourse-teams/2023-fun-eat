package com.funeat.recipe.application;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점4점_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.funeat.common.ServiceTest;
import com.funeat.member.domain.Member;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("NonAsciiCharacters")
class RecipeServiceTest extends ServiceTest {

    @Nested
    class create_성공_테스트 {

        @Test
        void 레시피를_추가할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var image1 = 이미지_생성();
            final var image2 = 이미지_생성();
            final var image3 = 이미지_생성();
            final var images = List.of(image1, image2, image3);

            final var request = 레시피추가요청_생성(productIds);

            final var expected = 레시피_생성(member);

            // when
            final var recipeId = recipeService.create(memberId, images, request);
            final var actual = recipeRepository.findById(recipeId).get();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(expected);
        }
    }

    @Test
    void 레시피의_상세_정보를_조회할_수_있다() {
        // given
        final var category = 카테고리_추가_요청(new Category("간편식사", CategoryType.FOOD));
        final var product1 = new Product("불닭볶음면", 1000L, "image.png", "엄청 매운 불닭", category);
        final var product2 = new Product("참치 삼김", 2000L, "image.png", "담백한 참치마요 삼김", category);
        final var product3 = new Product("스트링 치즈", 1500L, "image.png", "고소한 치즈", category);
        복수_상품_저장(product1, product2, product3);
        final var products = List.of(product1, product2, product3);
        final var author = new Member("author", "image.png", "1");
        단일_멤버_저장(author);
        final var authorId = author.getId();

        final var image1 = new MockMultipartFile("image1", "image1.jpg", "image/jpeg", new byte[]{1, 2, 3});
        final var image2 = new MockMultipartFile("image2", "image2.jpg", "image/jpeg", new byte[]{1, 2, 3});
        final var image3 = new MockMultipartFile("image3", "image3.jpg", "image/jpeg", new byte[]{1, 2, 3});

        final var productIds = List.of(product1.getId(), product2.getId(), product3.getId());
        final var request = new RecipeCreateRequest("제일로 맛있는 레시피", productIds,
                "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");

        final var recipeId = recipeService.create(authorId, List.of(image1, image2, image3), request);

        // when
        final var actual = recipeService.getRecipeDetail(authorId, recipeId);

        // then
        final var recipe = recipeRepository.findById(recipeId).get();
        final var expected = RecipeDetailResponse.toResponse(
                recipe, recipeImageRepository.findByRecipe(recipe),
                products, 4500L, false);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Nested
    class create_실패_테스트 {

        @Test
        void 존재하지_않는_멤버가_레시피를_추가하면_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var member = 멤버_멤버1_생성();
            final var wrongMemberId = 단일_멤버_저장(member) + 1L;

            final var image1 = 이미지_생성();
            final var image2 = 이미지_생성();
            final var image3 = 이미지_생성();
            final var images = List.of(image1, image2, image3);

            final var request = 레시피추가요청_생성(productIds);

            // when & then
            assertThatThrownBy(() -> recipeService.create(wrongMemberId, images, request))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 존재하지_않는_상품을_레시피에_추가하면_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var wrongProductIds = 상품_아이디_변환(product1, product2, product3);
            wrongProductIds.add(4L);

            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var image1 = 이미지_생성();
            final var image2 = 이미지_생성();
            final var image3 = 이미지_생성();
            final var images = List.of(image1, image2, image3);

            final var request = 레시피추가요청_생성(wrongProductIds);

            // when & then
            assertThatThrownBy(() -> recipeService.create(memberId, images, request))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    private MultipartFile 이미지_생성() {
        return new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{1, 2, 3});
    }

    private Category 카테고리_추가_요청(final Category category) {
        return categoryRepository.save(category);
    }

    private List<Long> 상품_아이디_변환(final Product... products) {
        return Stream.of(products)
                .map(Product::getId)
                .collect(Collectors.toList());
    }
}
