package com.funeat.product.presentation;

import com.funeat.product.application.ProductService;
import com.funeat.product.domain.SortOrderType;
import com.funeat.product.domain.SortType;
import com.funeat.product.dto.ProductsInCategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{category_id}/products")
    public ResponseEntity<ProductsInCategoryResponse> getAllProductsInCategory(
            @PathVariable final Long category_id,
            @RequestParam final SortType option,
            @RequestParam final SortOrderType order,
            @RequestParam final Integer page
            ) {
        ProductsInCategoryResponse response = productService.getAllProductsInCategory(category_id, option, order, page);
        return ResponseEntity.ok(response);
    }
}
