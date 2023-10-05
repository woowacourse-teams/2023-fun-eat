package com.funeat.admin.application;

import static com.funeat.product.exception.CategoryErrorCode.CATEGORY_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;

import com.funeat.admin.dto.AdminCategoryResponse;
import com.funeat.admin.dto.AdminProductResponse;
import com.funeat.admin.dto.AdminProductSearchResponse;
import com.funeat.admin.dto.ProductCreateRequest;
import com.funeat.admin.dto.ProductSearchCondition;
import com.funeat.admin.dto.ProductUpdateRequest;
import com.funeat.admin.repository.AdminProductRepository;
import com.funeat.admin.specification.AdminProductSpecification;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.exception.CategoryException.CategoryNotFoundException;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final ProductRepository productRepository;
    private final AdminProductRepository adminProductRepository;
    private final CategoryRepository categoryRepository;

    public AdminService(final ProductRepository productRepository, final AdminProductRepository adminProductRepository,
                        final CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.adminProductRepository = adminProductRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Long addProduct(final ProductCreateRequest request) {
        final Category findCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 확인해주세요"));

        final Product product = Product.create(request.getName(), request.getPrice(), request.getContent(),
                findCategory);

        return productRepository.save(product).getId();
    }

    public List<AdminCategoryResponse> getAllCategories() {
        final List<Category> findCategories = categoryRepository.findAll();

        return findCategories.stream()
                .map(AdminCategoryResponse::toResponse)
                .collect(Collectors.toList());
    }

    public AdminProductSearchResponse getSearchProducts(final ProductSearchCondition condition,
                                                        final Pageable pageable) {
        final Specification<Product> specification = AdminProductSpecification.searchBy(condition);

        final Page<Product> findProducts = adminProductRepository.findAllForPagination(specification, pageable,
                condition.getTotalElements());

        final List<AdminProductResponse> productResponses = findProducts.stream()
                .map(AdminProductResponse::toResponse)
                .collect(Collectors.toList());

        final Boolean isLastPage = isLastPage(findProducts, condition.getPrePage());

        return new AdminProductSearchResponse(productResponses, findProducts.getTotalElements(), isLastPage);
    }

    private <T> boolean isLastPage(final Page<T> findProducts, Long prePage) {
        return prePage * 10 + findProducts.getContent().size() == findProducts.getTotalElements();
    }

    @Transactional
    public void updateProduct(final Long productId, final ProductUpdateRequest request) {
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final Category findCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND, request.getCategoryId()));

        findProduct.update(request.getName(), request.getContent(), request.getPrice(), findCategory);
    }
}
