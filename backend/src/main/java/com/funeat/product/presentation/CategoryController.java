package com.funeat.product.presentation;

import com.funeat.product.domain.CategoryType;
import com.funeat.product.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "02.Category", description = "카테고리 기능")
public interface CategoryController {

    @Operation(summary = "해당 type 카테고리 전체 조회", description = "FOOD 또는 STORE 를 받아 해당 카테고리를 전체 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "해당 type 카테고리 전체 조회 성공."
    )
    @GetMapping
    ResponseEntity<List<CategoryResponse>> getAllCategoriesByType(@RequestParam final CategoryType type);
}
