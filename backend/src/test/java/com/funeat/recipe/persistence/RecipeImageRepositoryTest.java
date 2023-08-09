package com.funeat.recipe.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.domain.ProductRecipe;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRecipeRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RecipeImageRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductRecipeRepository productRecipeRepository;

    @Autowired
    private RecipeImageRepository recipeImageRepository;

    @Test
    void 레시피에_사용된_이미지들을_조회할_수_있다() {
        // given
        final var category = 카테고리_추가_요청(new Category("간편식사", CategoryType.FOOD));
        final var product1 = 상품_추가_요청(new Product("불닭볶음면", 1000L, "image.png", "엄청 매운 불닭", category));
        final var product2 = 상품_추가_요청(new Product("참치 삼김", 2000L, "image.png", "담백한 참치마요 삼김", category));
        final var product3 = 상품_추가_요청(new Product("스트링 치즈", 1500L, "image.png", "고소한 치즈", category));
        final var member = 멤버_추가_요청(new Member("test", "image.png", "1"));
        List<Product> expected = List.of(product1, product2, product3);

        Recipe recipe = 레시피_추가_요청(new Recipe("레시피1", "밥 넣고 밥 넣자", member));
        expected.forEach(it -> 레시피에_사용된_상품_추가_요청(new ProductRecipe(it, recipe)));

        RecipeImage image1 = new RecipeImage("1번사진", recipe);
        RecipeImage image2 = new RecipeImage("2번사진", recipe);
        RecipeImage image3 = new RecipeImage("3번사진", recipe);
        복수_레시피_이미지_추가_요청(List.of(image1, image2, image3));

        // when
        List<RecipeImage> images = recipeImageRepository.findByRecipe(recipe);

        // then
        assertThat(images.size()).isEqualTo(3);
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

    private Recipe 레시피_추가_요청(final Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    private ProductRecipe 레시피에_사용된_상품_추가_요청(final ProductRecipe productRecipe) {
        return productRecipeRepository.save(productRecipe);
    }

    private void 복수_레시피_이미지_추가_요청(final List<RecipeImage> images) {
        recipeImageRepository.saveAll(images);
    }
}
