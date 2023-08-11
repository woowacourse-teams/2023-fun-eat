package com.funeat.recipe.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.persistence.RecipeImageRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RecipeServiceTest {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeImageRepository recipeImageRepository;

    @Test
    void 레시피를_추가할_수_있다() {
        // given
        final var category = 카테고리_추가_요청(new Category("간편식사", CategoryType.FOOD));
        final var product1 = 상품_추가_요청(new Product("불닭볶음면", 1000L, "image.png", "엄청 매운 불닭", category));
        final var product2 = 상품_추가_요청(new Product("참치 삼김", 2000L, "image.png", "담백한 참치마요 삼김", category));
        final var product3 = 상품_추가_요청(new Product("스트링 치즈", 1500L, "image.png", "고소한 치즈", category));
        final var member = 멤버_추가_요청(new Member("test", "image.png", "1"));

        final var image1 = new MockMultipartFile("image1", "image1.jpg", "image/jpeg", new byte[]{1, 2, 3});
        final var image2 = new MockMultipartFile("image2", "image2.jpg", "image/jpeg", new byte[]{1, 2, 3});
        final var image3 = new MockMultipartFile("image3", "image3.jpg", "image/jpeg", new byte[]{1, 2, 3});

        final var request = new RecipeCreateRequest("제일로 맛있는 레시피",
                List.of(product1.getId(), product2.getId(), product3.getId()),
                "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");

        // when
        final var recipeId = recipeService.create(member.getId(), List.of(image1, image2, image3), request);
        final var actual = recipeRepository.findById(recipeId).get();

        // then
        final var expected = new Recipe("제일로 맛있는 레시피", "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!", member);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void 레시피의_상세_정보를_조회할_수_있다() {
        // given
        final var category = 카테고리_추가_요청(new Category("간편식사", CategoryType.FOOD));
        final var product1 = 상품_추가_요청(new Product("불닭볶음면", 1000L, "image.png", "엄청 매운 불닭", category));
        final var product2 = 상품_추가_요청(new Product("참치 삼김", 2000L, "image.png", "담백한 참치마요 삼김", category));
        final var product3 = 상품_추가_요청(new Product("스트링 치즈", 1500L, "image.png", "고소한 치즈", category));
        final var products = List.of(product1, product2, product3);
        final var author = 멤버_추가_요청(new Member("author", "image.png", "1"));
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

    private Category 카테고리_추가_요청(final Category category) {
        return categoryRepository.save(category);
    }

    private Product 상품_추가_요청(final Product product) {
        return productRepository.save(product);
    }

    private Member 멤버_추가_요청(final Member member) {
        return memberRepository.save(member);
    }
}
