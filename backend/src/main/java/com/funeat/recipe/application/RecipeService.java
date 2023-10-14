package com.funeat.recipe.application;

import static com.funeat.member.exception.MemberErrorCode.MEMBER_DUPLICATE_FAVORITE;
import static com.funeat.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static com.funeat.recipe.exception.RecipeErrorCode.RECIPE_NOT_FOUND;

import com.funeat.comment.domain.Comment;
import com.funeat.comment.persistence.CommentRepository;
import com.funeat.comment.specification.CommentSpecification;
import com.funeat.common.ImageUploader;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.member.dto.MemberRecipeDto;
import com.funeat.member.dto.MemberRecipeProductDto;
import com.funeat.member.dto.MemberRecipesResponse;
import com.funeat.member.exception.MemberException.MemberDuplicateFavoriteException;
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
import com.funeat.recipe.dto.RankingRecipeDto;
import com.funeat.recipe.dto.RankingRecipesResponse;
import com.funeat.recipe.dto.RecipeAuthorDto;
import com.funeat.recipe.dto.RecipeCommentCondition;
import com.funeat.recipe.dto.RecipeCommentCreateRequest;
import com.funeat.recipe.dto.RecipeCommentResponse;
import com.funeat.recipe.dto.RecipeCommentsResponse;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.dto.RecipeDto;
import com.funeat.recipe.dto.RecipeFavoriteRequest;
import com.funeat.recipe.dto.SearchRecipeResultDto;
import com.funeat.recipe.dto.SearchRecipeResultsResponse;
import com.funeat.recipe.dto.SortingRecipesResponse;
import com.funeat.recipe.exception.RecipeException.RecipeNotFoundException;
import com.funeat.recipe.persistence.RecipeImageRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class RecipeService {

    private static final int THREE = 3;
    private static final int TOP = 0;
    private static final int RECIPE_COMMENT_PAGE_SIZE = 10;
    private static final int DEFAULT_CURSOR_PAGINATION_SIZE = 11;

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ProductRecipeRepository productRecipeRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeImageRepository recipeImageRepository;
    private final RecipeFavoriteRepository recipeFavoriteRepository;
    private final CommentRepository commentRepository;
    private final ImageUploader imageUploader;

    public RecipeService(final MemberRepository memberRepository, final ProductRepository productRepository,
                         final ProductRecipeRepository productRecipeRepository, final RecipeRepository recipeRepository,
                         final RecipeImageRepository recipeImageRepository,
                         final RecipeFavoriteRepository recipeFavoriteRepository,
                         final CommentRepository commentRepository, final ImageUploader imageUploader) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.productRecipeRepository = productRecipeRepository;
        this.recipeRepository = recipeRepository;
        this.recipeImageRepository = recipeImageRepository;
        this.recipeFavoriteRepository = recipeFavoriteRepository;
        this.commentRepository = commentRepository;
        this.imageUploader = imageUploader;
    }

    @Transactional
    public Long create(final Long memberId, final List<MultipartFile> images, final RecipeCreateRequest request) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));

        final Recipe savedRecipe = recipeRepository.save(new Recipe(request.getTitle(), request.getContent(), member));
        request.getProductIds()
                .stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId)))
                .forEach(product -> productRecipeRepository.save(new ProductRecipe(product, savedRecipe)));

        if (Objects.nonNull(images)) {
            images.forEach(image -> {
                final String imageUrl = imageUploader.upload(image);
                recipeImageRepository.save(new RecipeImage(imageUrl, savedRecipe));
            });
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
                    final List<MemberRecipeProductDto> memberRecipeProductDtos = productsByRecipe.stream()
                            .map(MemberRecipeProductDto::toDto)
                            .collect(Collectors.toList());
                    return MemberRecipeDto.toDto(recipe, findRecipeImages, memberRecipeProductDtos);
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

    @Transactional
    public void likeRecipe(final Long memberId, final Long recipeId, final RecipeFavoriteRequest request) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Recipe recipe = recipeRepository.findByIdForUpdate(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(RECIPE_NOT_FOUND, recipeId));

        final RecipeFavorite recipeFavorite = recipeFavoriteRepository.findByMemberAndRecipe(member, recipe)
                .orElseGet(() -> createAndSaveRecipeFavorite(member, recipe, request.getFavorite()));

        recipeFavorite.updateFavorite(request.getFavorite());
    }

    private RecipeFavorite createAndSaveRecipeFavorite(final Member member, final Recipe recipe,
                                                       final Boolean favorite) {
        try {
            final RecipeFavorite recipeFavorite = RecipeFavorite.create(member, recipe, favorite);
            return recipeFavoriteRepository.save(recipeFavorite);
        } catch (final DataIntegrityViolationException e) {
            throw new MemberDuplicateFavoriteException(MEMBER_DUPLICATE_FAVORITE, member.getId());
        }
    }

    public SearchRecipeResultsResponse getSearchResults(final String query, final Pageable pageable) {
        final Page<Recipe> recipePages = recipeRepository.findAllByProductNameContaining(query, pageable);

        final PageDto page = PageDto.toDto(recipePages);
        final List<SearchRecipeResultDto> dtos = recipePages.stream()
                .map(recipe -> {
                    final List<RecipeImage> findRecipeImages = recipeImageRepository.findByRecipe(recipe);
                    final List<Product> productsByRecipe = productRecipeRepository.findProductByRecipe(recipe);
                    return SearchRecipeResultDto.toDto(recipe, findRecipeImages, productsByRecipe);
                })
                .collect(Collectors.toList());
        return SearchRecipeResultsResponse.toResponse(page, dtos);
    }

    public RankingRecipesResponse getTop3Recipes() {
        final List<Recipe> recipes = recipeRepository.findRecipesByOrderByFavoriteCountDesc(PageRequest.of(TOP, THREE));

        final List<RankingRecipeDto> dtos = recipes.stream()
                .map(recipe -> {
                    final List<RecipeImage> findRecipeImages = recipeImageRepository.findByRecipe(recipe);
                    final RecipeAuthorDto author = RecipeAuthorDto.toDto(recipe.getMember());
                    return RankingRecipeDto.toDto(recipe, findRecipeImages, author);
                })
                .collect(Collectors.toList());
        return RankingRecipesResponse.toResponse(dtos);
    }

    @Transactional
    public Long writeCommentOfRecipe(final Long memberId, final Long recipeId,
                                     final RecipeCommentCreateRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));

        final Recipe findRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(RECIPE_NOT_FOUND, recipeId));

        final Comment comment = new Comment(findRecipe, findMember, request.getComment());

        final Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    public RecipeCommentsResponse getCommentsOfRecipe(final Long recipeId, final RecipeCommentCondition condition) {
        final Recipe findRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(RECIPE_NOT_FOUND, recipeId));

        final Specification<Comment> specification = CommentSpecification.findAllByRecipe(findRecipe,
                condition.getLastId());

        final PageRequest pageable = PageRequest.of(0, DEFAULT_CURSOR_PAGINATION_SIZE, Sort.by("id").descending());

        final Page<Comment> commentPaginationResult = commentRepository.findAllForPagination(specification, pageable,
                condition.getTotalElements());

        final List<RecipeCommentResponse> recipeCommentResponses = getRecipeCommentResponses(
                commentPaginationResult.getContent());

        final Boolean hasNext = hasNextPage(commentPaginationResult);

        return RecipeCommentsResponse.toResponse(recipeCommentResponses, hasNext,
                commentPaginationResult.getTotalElements());
    }

    private List<RecipeCommentResponse> getRecipeCommentResponses(final List<Comment> findComments) {
        final List<RecipeCommentResponse> recipeCommentResponses = new ArrayList<>();
        final int resultSize = getResultSize(findComments);
        final List<Comment> comments = findComments.subList(0, resultSize);

        for (final Comment comment : comments) {
            final RecipeCommentResponse recipeCommentResponse = RecipeCommentResponse.toResponse(comment);
            recipeCommentResponses.add(recipeCommentResponse);
        }
        return recipeCommentResponses;
    }

    private int getResultSize(final List<Comment> findComments) {
        if (findComments.size() < DEFAULT_CURSOR_PAGINATION_SIZE) {
            return findComments.size();
        }
        return RECIPE_COMMENT_PAGE_SIZE;
    }

    private Boolean hasNextPage(final Page<Comment> findComments) {
        return findComments.getContent().size() > RECIPE_COMMENT_PAGE_SIZE;
    }
}
