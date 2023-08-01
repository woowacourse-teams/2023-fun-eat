package com.funeat.product.presentation;

import com.funeat.product.application.ProductService;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductsInCategoryResponse;
import com.funeat.product.dto.RankingProductsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductApiController implements ProductController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
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

    @GetMapping("/ranks/products")
    public ResponseEntity<RankingProductsResponse> getRankingProducts() {
        final RankingProductsResponse response = productService.getTop3Products();
        return ResponseEntity.ok(response);
    }
}
