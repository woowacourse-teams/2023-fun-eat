package com.funeat.recipe.application;

import static com.funeat.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static com.funeat.recipe.exception.RecipeErrorCode.RECIPE_NOT_FOUND;

import com.funeat.common.ImageService;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberRecipeDto;
import com.funeat.member.dto.MemberRecipesResponse;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.RecipeFavoriteRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.domain.ProductRecipe;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.product.persistence.ProductRecipeRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.dto.RecipeDto;
import com.funeat.recipe.dto.SortingRecipesResponse;
import com.funeat.recipe.exception.RecipeException.RecipeNotFoundException;
import com.funeat.recipe.persistence.RecipeImageRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));

        final Recipe savedRecipe = recipeRepository.save(new Recipe(request.getTitle(), request.getContent(), member));
        request.getProductIds()
                .stream()
                .map(it -> productRepository.findById(it)
                        .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, it)))
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
                .orElseThrow(() -> new RecipeNotFoundException(RECIPE_NOT_FOUND, recipeId));
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
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        return recipeFavoriteRepository.existsByMemberAndRecipeAndFavoriteTrue(member, recipe);
    }

    public MemberRecipesResponse findRecipeByMember(final Long memberId, final Pageable pageable) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));

        final Page<Recipe> sortedRecipePages = recipeRepository.findRecipesByMember(findMember, pageable);

        final PageDto page = PageDto.toDto(sortedRecipePages);
        final List<MemberRecipeDto> dtos = sortedRecipePages.stream()
                .map(recipe -> {
                    final List<RecipeImage> findRecipeImages = recipeImageRepository.findByRecipe(recipe);
                    final List<Product> productsByRecipe = productRecipeRepository.findProductByRecipe(recipe);
                    return MemberRecipeDto.toDto(recipe, findRecipeImages, productsByRecipe);
                })
                .collect(Collectors.toList());

        return MemberRecipesResponse.toResponse(page, dtos);
    }

    public SortingRecipesResponse getSortingRecipes(final Pageable pageable) {
        final Page<Recipe> pages = recipeRepository.findAll(pageable);

        final PageDto page = PageDto.toDto(pages);
        final List<RecipeDto> recipes = pages.getContent().stream()
                .map(recipe -> {
                    final List<RecipeImage> images = recipeImageRepository.findByRecipe(recipe);
                    final List<Product> products = productRecipeRepository.findProductByRecipe(recipe);
                    return RecipeDto.toDto(recipe, images, products);
                })
                .collect(Collectors.toList());

        return SortingRecipesResponse.toResponse(page, recipes);
    }
}
