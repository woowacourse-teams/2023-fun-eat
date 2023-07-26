package com.funeat.product.application;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductsInCategoryPageDto;
import com.funeat.product.dto.ProductsInCategoryResponse;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.domain.Tag;
import java.util.List;
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
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;

    public ProductService(final CategoryRepository categoryRepository, final ProductRepository productRepository,
                          final ReviewRepository reviewRepository, final ReviewTagRepository reviewTagRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.reviewTagRepository = reviewTagRepository;
    }

    public ProductsInCategoryResponse getAllProductsInCategory(final Long categoryId,
                                                               final Pageable pageable) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(IllegalArgumentException::new);

        final Page<ProductInCategoryDto> pages;
        if (pageable.getSort().getOrderFor("reviewCount") == null) {
            pages = productRepository.findAllByCategory(category, pageable);
        } else {
            PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
            pages = productRepository.findAllByCategoryOrderByReviewCountDesc(category, pageRequest);
        }

        final ProductsInCategoryPageDto pageDto = ProductsInCategoryPageDto.toDto(pages);
        final List<ProductInCategoryDto> productDtos = pages.getContent();

        return ProductsInCategoryResponse.toResponse(pageDto, productDtos);
    }

    public ProductResponse findProductDetail(final Long productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        final List<Tag> tags = reviewTagRepository.findTop3TagsByReviewIn(productId, PageRequest.of(TOP, THREE));

        return ProductResponse.toResponse(product, tags);
    }
}
