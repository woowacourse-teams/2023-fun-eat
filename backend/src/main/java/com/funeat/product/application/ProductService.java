package com.funeat.product.application;

import static com.funeat.product.exception.CategoryErrorCode.CATEGORY_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;

import com.funeat.common.dto.PageDto;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductReviewCountDto;
import com.funeat.product.dto.ProductsInCategoryResponse;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.product.dto.RankingProductsResponse;
import com.funeat.product.dto.SearchProductDto;
import com.funeat.product.dto.SearchProductResultDto;
import com.funeat.product.dto.SearchProductResultsResponse;
import com.funeat.product.dto.SearchProductsResponse;
import com.funeat.product.exception.CategoryException.CategoryNotFoundException;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRecipeRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import com.funeat.recipe.dto.RecipeDto;
import com.funeat.recipe.dto.SortingRecipesResponse;
import com.funeat.recipe.persistence.RecipeImageRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.domain.Tag;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private static final int THREE = 3;
    private static final int TOP = 0;
    public static final String REVIEW_COUNT = "reviewCount";
    private static final int RANKING_SIZE = 3;

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final ReviewRepository reviewRepository;
    private final ProductRecipeRepository productRecipeRepository;
    private final RecipeImageRepository recipeImageRepository;
    private final RecipeRepository recipeRepository;

    public ProductService(final CategoryRepository categoryRepository, final ProductRepository productRepository,
                          final ReviewTagRepository reviewTagRepository, final ReviewRepository reviewRepository,
                          final ProductRecipeRepository productRecipeRepository,
                          final RecipeImageRepository recipeImageRepository, final RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.reviewTagRepository = reviewTagRepository;
        this.reviewRepository = reviewRepository;
        this.productRecipeRepository = productRecipeRepository;
        this.recipeImageRepository = recipeImageRepository;
        this.recipeRepository = recipeRepository;
    }

    public ProductsInCategoryResponse getAllProductsInCategory(final Long categoryId,
                                                               final Pageable pageable) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND, categoryId));

        final Slice<ProductInCategoryDto> pages = productRepository.findAllByCategory(category, pageable);
        final List<ProductInCategoryDto> productDtos = pages.getContent();

        return ProductsInCategoryResponse.toResponse(pages.hasNext(), productDtos);
    }

    public ProductResponse findProductDetail(final Long productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));
        final Long reviewCount = reviewRepository.countByProduct(product);
        final List<Tag> tags = reviewTagRepository.findTop3TagsByReviewIn(productId, PageRequest.of(TOP, THREE));

        return ProductResponse.toResponse(product, reviewCount, tags);
    }

    public RankingProductsResponse getTop3Products() {
        final List<ProductReviewCountDto> productsAndReviewCounts = productRepository.findAllByAverageRatingGreaterThan3();
        final Comparator<ProductReviewCountDto> rankingScoreComparator = Comparator.comparing(
                (ProductReviewCountDto it) -> it.getProduct().calculateRankingScore(it.getReviewCount())
        ).reversed();

        final List<RankingProductDto> rankingProductDtos = productsAndReviewCounts.stream()
                .sorted(rankingScoreComparator)
                .limit(RANKING_SIZE)
                .map(it -> RankingProductDto.toDto(it.getProduct()))
                .collect(Collectors.toList());

        return RankingProductsResponse.toResponse(rankingProductDtos);
    }

    public SearchProductsResponse searchProducts(final String query, final Pageable pageable) {
        final Page<Product> products = productRepository.findAllByNameContaining(query, pageable);

        final PageDto pageDto = PageDto.toDto(products);
        final List<SearchProductDto> productDtos = products.stream()
                .map(SearchProductDto::toDto)
                .collect(Collectors.toList());

        return SearchProductsResponse.toResponse(pageDto, productDtos);
    }

    public SearchProductResultsResponse getSearchResults(final String query, final Pageable pageable) {
        final Page<ProductReviewCountDto> products = productRepository.findAllWithReviewCountByNameContaining(query,
                pageable);

        final PageDto pageDto = PageDto.toDto(products);
        final List<SearchProductResultDto> resultDtos = products.stream()
                .map(it -> SearchProductResultDto.toDto(it.getProduct(), it.getReviewCount()))
                .collect(Collectors.toList());

        return SearchProductResultsResponse.toResponse(pageDto, resultDtos);
    }

    public SortingRecipesResponse getProductRecipes(final Long productId, final Pageable pageable) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final Page<Recipe> recipes = recipeRepository.findRecipesByProduct(product, pageable);

        final PageDto pageDto = PageDto.toDto(recipes);
        final List<RecipeDto> recipeDtos = recipes.stream()
                .map(recipe -> {
                    final List<RecipeImage> images = recipeImageRepository.findByRecipe(recipe);
                    final List<Product> products = productRecipeRepository.findProductByRecipe(recipe);
                    return RecipeDto.toDto(recipe, images, products);
                })
                .collect(Collectors.toList());
        return SortingRecipesResponse.toResponse(pageDto, recipeDtos);
    }
}
