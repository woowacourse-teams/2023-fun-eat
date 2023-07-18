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
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

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
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(IllegalArgumentException::new);

        Page<Product> productPages = productRepository.findAllByCategory(category, pageable);

        ProductsInCategoryPageDto pageDto = ProductsInCategoryPageDto.toDto(productPages);
        List<ProductInCategoryDto> productDtos = productPages.getContent()
                .stream()
                .map(it -> ProductInCategoryDto.toDto(it, reviewRepository.countByProduct(it)))
                .collect(Collectors.toList());

        return new ProductsInCategoryResponse(pageDto, productDtos);
    }

    public ProductResponse findProductDetail(final Long productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);
        final List<Tag> tags = reviewTagRepository.findTop3TagsByReviewIn(productId, PageRequest.of(0, 3));
        return ProductResponse.toResponse(product, tags);
    }
}
