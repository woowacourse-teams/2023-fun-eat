package com.funeat.product.presentation;

import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductsInCategoryResponse;
import com.funeat.product.dto.RankingProductsResponse;
import com.funeat.product.dto.SearchProductResultsResponse;
import com.funeat.product.dto.SearchProductsResponse;
import com.funeat.recipe.dto.SortingRecipesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "01.Product", description = "상품 기능")
public interface ProductController {

    @Operation(summary = "해당 카테고리 상품 조회", description = "해당 카테고리의 상품을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "해당 카테고리 상품 조회 성공."
    )
    @GetMapping
    ResponseEntity<ProductsInCategoryResponse> getAllProductsInCategory(
            @PathVariable(name = "category_id") final Long categoryId, @PageableDefault final Pageable pageable
    );

    @Operation(summary = "해당 상품 상세 조회", description = "해당 상품 상세정보를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "해당 상품 상세 조회 성공."
    )
    @GetMapping
    ResponseEntity<ProductResponse> getProductDetail(@PathVariable final Long productId);

    @Operation(summary = "전체 상품 랭킹 조회", description = "전체 상품들 중에서 랭킹 TOP3를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "전체 상품 랭킹 조회 성공."
    )
    @GetMapping
    ResponseEntity<RankingProductsResponse> getRankingProducts();

    @Operation(summary = "상품 자동 완성 검색", description = "공통 상품 및 PB 상품들의 이름 중에 해당하는 문자열이 포함되어 있는지 검색한다.")
    @ApiResponse(
            responseCode = "200",
            description = "상품 검색 성공."
    )
    @GetMapping
    ResponseEntity<SearchProductsResponse> searchProducts(@RequestParam final String query,
                                                          @RequestParam final Long lastId);

    @Operation(summary = "상품 검색 결과 조회", description = "문자열을 받아 상품을 검색하고 검색 결과들을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "상품 검색 결과 조회 성공."
    )
    @GetMapping
    ResponseEntity<SearchProductResultsResponse> getSearchResults(@RequestParam final String query,
                                                                  @PageableDefault final Pageable pageable);

    @Operation(summary = "해당 상품 꿀조합 목록 조회", description = "해당 상품이 포함된 꿀조합 목록을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "해당 상품 꿀조합 목록 조회 성공."
    )
    @GetMapping
    ResponseEntity<SortingRecipesResponse> getProductRecipes(@PathVariable final Long productId,
                                                             @PageableDefault final Pageable pageable);
}
