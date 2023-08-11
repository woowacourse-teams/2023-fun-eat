package com.funeat.recipe.application;

import com.funeat.common.ImageService;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.RecipeFavoriteRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.domain.ProductRecipe;
import com.funeat.product.persistence.ProductRecipeRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.persistence.RecipeImageRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class RecipeService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ProductRecipeRepository productRecipeRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeImageRepository recipeImageRepository;
    private final RecipeFavoriteRepository recipeFavoriteRepository;
    private final ImageService imageService;

    public RecipeService(final MemberRepository memberRepository, final ProductRepository productRepository,
                         final ProductRecipeRepository productRecipeRepository, final RecipeRepository recipeRepository,
                         final RecipeImageRepository recipeImageRepository,
                         final RecipeFavoriteRepository recipeFavoriteRepository,
                         final ImageService imageService) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.productRecipeRepository = productRecipeRepository;
        this.recipeRepository = recipeRepository;
        this.recipeImageRepository = recipeImageRepository;
        this.recipeFavoriteRepository = recipeFavoriteRepository;
        this.imageService = imageService;
    }

    @Transactional
    public Long create(final Long memberId, final List<MultipartFile> images, final RecipeCreateRequest request) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        final Recipe savedRecipe = recipeRepository.save(new Recipe(request.getTitle(), request.getContent(), member));
        request.getProductIds()
                .stream()
                .map(it -> productRepository.findById(it)
                        .orElseThrow(IllegalArgumentException::new))
                .forEach(it -> productRecipeRepository.save(new ProductRecipe(it, savedRecipe)));

        if (Objects.nonNull(images)) {
            images.stream()
                    .peek(it -> recipeImageRepository.save(new RecipeImage(it.getOriginalFilename(), savedRecipe)))
                    .forEach(imageService::upload);
        }

        return savedRecipe.getId();
    }

    public RecipeDetailResponse getRecipeDetail(final Long memberId, final Long recipeId) {
        final Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(IllegalArgumentException::new);
        final List<RecipeImage> images = recipeImageRepository.findByRecipe(recipe);
        final List<Product> products = productRecipeRepository.findProductByRecipe(recipe);
        final Long totalPrice = products.stream()
                .mapToLong(Product::getPrice)
                .sum();

        final Boolean favorite = calculateFavoriteChecked(memberId, recipe);

        return RecipeDetailResponse.toResponse(recipe, images, products, totalPrice, favorite);
    }

    private Boolean calculateFavoriteChecked(final Long memberId, final Recipe recipe) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);
        return recipeFavoriteRepository.existsByMemberAndRecipeAndFavoriteTrue(member, recipe);
    }
}
