package com.funeat.product.application;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductsInCategoryPageDto;
import com.funeat.product.dto.ProductsInCategoryResponse;
import com.funeat.review.persistence.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository,
                          ReviewRepository reviewRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public ProductsInCategoryResponse getAllProductsInCategory(Long category_id,
                                                               Pageable pageable) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(IllegalArgumentException::new);

        Page<Product> productPages = productRepository.findAllByCategory(category, pageable);

        ProductsInCategoryPageDto pageDto = ProductsInCategoryPageDto.toDto(productPages);
        List<ProductInCategoryDto> productDtos = productPages.getContent()
                .stream()
                .map(it -> ProductInCategoryDto.toDto(it, reviewRepository.countByProduct(it)))
                .collect(Collectors.toList());

        return new ProductsInCategoryResponse(pageDto, productDtos);
    }
}
