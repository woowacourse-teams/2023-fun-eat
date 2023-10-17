package com.funeat.acceptance.common;

import com.funeat.comment.persistence.CommentRepository;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.RecipeFavoriteRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRecipeRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.persistence.RecipeImageRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected TagRepository tagRepository;

    @Autowired
    protected ReviewTagRepository reviewTagRepository;

    @Autowired
    protected ReviewFavoriteRepository reviewFavoriteRepository;

    @Autowired
    public RecipeRepository recipeRepository;

    @Autowired
    public RecipeImageRepository recipeImageRepository;

    @Autowired
    protected ProductRecipeRepository productRecipeRepository;

    @Autowired
    public RecipeFavoriteRepository recipeFavoriteRepository;

    @Autowired
    protected CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected Long 단일_상품_저장(final Product product) {
        return productRepository.save(product).getId();
    }

    protected Long 단일_카테고리_저장(final Category category) {
        return categoryRepository.save(category).getId();
    }

    protected Long 단일_멤버_저장(final Member member) {
        return memberRepository.save(member).getId();
    }

    protected Long 단일_태그_저장(final Tag tag) {
        return tagRepository.save(tag).getId();
    }
}
