package com.funeat.product.presentation;

import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductsInCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "01.Product", description = "상품 기능")
public interface ProductController {

    @Operation(summary = "해당 카테고리 상품 조회", description = "해당 카테고리의 상품을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "해당 카테고리 상품 조회 성공."
    )
    @GetMapping
    ResponseEntity<ProductsInCategoryResponse> getAllProductsInCategory(
            @PathVariable(name = "category_id") final Long categoryId, @PageableDefault Pageable pageable
    );

    @Operation(summary = "해당 상품 상세 조회", description = "해당 상품 상세정보를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "해당 상품 상세 조회 성공."
    )
    @GetMapping
    ResponseEntity<ProductResponse> getProductDetail(@PathVariable final Long productId);
}
