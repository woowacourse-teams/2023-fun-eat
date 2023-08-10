package com.funeat.product.application;

import static com.funeat.product.exception.CategoryErrorCode.CATEGORY_NOF_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOF_FOUND;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductReviewCountDto;
import com.funeat.product.dto.ProductsInCategoryPageDto;
import com.funeat.product.dto.ProductsInCategoryResponse;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.product.dto.RankingProductsResponse;
import com.funeat.product.exception.CategoryException.CategoryNotFoundException;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.domain.Tag;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private static final int THREE = 3;
    private static final int TOP = 0;
    public static final String REVIEW_COUNT = "reviewCount";

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ReviewTagRepository reviewTagRepository;

    public ProductService(final CategoryRepository categoryRepository, final ProductRepository productRepository,
                          final ReviewTagRepository reviewTagRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.reviewTagRepository = reviewTagRepository;
    }

    public ProductsInCategoryResponse getAllProductsInCategory(final Long categoryId,
                                                               final Pageable pageable) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOF_FOUND, categoryId));

        final Page<ProductInCategoryDto> pages = getAllProductsInCategory(pageable, category);

        final ProductsInCategoryPageDto pageDto = ProductsInCategoryPageDto.toDto(pages);
        final List<ProductInCategoryDto> productDtos = pages.getContent();

        return ProductsInCategoryResponse.toResponse(pageDto, productDtos);
    }

    private Page<ProductInCategoryDto> getAllProductsInCategory(final Pageable pageable, final Category category) {
        if (pageable.getSort().getOrderFor(REVIEW_COUNT) != null) {
            final PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
            return productRepository.findAllByCategoryOrderByReviewCountDesc(category, pageRequest);
        }
        return productRepository.findAllByCategory(category, pageable);
    }

    public ProductResponse findProductDetail(final Long productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOF_FOUND, productId));

        final List<Tag> tags = reviewTagRepository.findTop3TagsByReviewIn(productId, PageRequest.of(TOP, THREE));

        return ProductResponse.toResponse(product, tags);
    }

    public RankingProductsResponse getTop3Products() {
        final List<ProductReviewCountDto> productsAndReviewCounts = productRepository.findAllByAverageRatingGreaterThan3();
        final Comparator<ProductReviewCountDto> rankingScoreComparator = Comparator.comparing(
                (ProductReviewCountDto it) -> it.getProduct().calculateRankingScore(it.getReviewCount())
        ).reversed();

        final List<RankingProductDto> rankingProductDtos = productsAndReviewCounts.stream()
                .sorted(rankingScoreComparator)
                .limit(3)
                .map(it -> RankingProductDto.toDto(it.getProduct()))
                .collect(Collectors.toList());

        return RankingProductsResponse.toResponse(rankingProductDtos);
    }
}
