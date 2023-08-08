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
import org.springframework.web.multipart.MultipartFile;

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

    @Test
    void 레시피를_추가할_수_있다() {
        // given
        Category category = categoryRepository.save(new Category("간편식사", CategoryType.FOOD));
        Product product1 = productRepository.save(new Product("불닭볶음면", 1000L, "image.png", "엄청 매운 불닭", category));
        Product product2 = productRepository.save(new Product("참치 삼김", 2000L, "image.png", "담백한 참치마요 삼김", category));
        Product product3 = productRepository.save(new Product("스트링 치즈", 1500L, "image.png", "고소한 치즈", category));
        Member member = memberRepository.save(new Member("test", "image.png", "1"));

        List<MultipartFile> images = List.of(
                new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{1, 2, 3}),
                new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{1, 2, 3}),
                new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{1, 2, 3})
        );

        RecipeCreateRequest request = new RecipeCreateRequest("제일로 맛있는 레시피",
                List.of(product1.getId(), product2.getId(), product3.getId()),
                "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");

        // when
        Long recipeId = recipeService.create(member.getId(), images, request);
        Recipe actual = recipeRepository.findById(recipeId).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(
                        new Recipe("제일로 맛있는 레시피", "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!", member)
                );
    }

}
