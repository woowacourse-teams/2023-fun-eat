package com.funeat.product.presentation;

import com.funeat.product.application.ProductService;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductsInCategoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController implements ProductApiController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/categories/{category_id}/products")
    public ResponseEntity<ProductsInCategoryResponse> getAllProductsInCategory(
            @PathVariable(name = "category_id") final Long categoryId,
            @PageableDefault Pageable pageable
    ) {
        final ProductsInCategoryResponse response = productService.getAllProductsInCategory(categoryId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable final Long productId) {
        final ProductResponse response = productService.findProductDetail(productId);
        return ResponseEntity.ok(response);
    }
}
