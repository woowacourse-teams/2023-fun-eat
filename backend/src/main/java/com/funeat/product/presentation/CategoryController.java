package com.funeat.product.presentation;

import com.funeat.product.application.CategoryService;
import com.funeat.product.domain.CategoryType;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategoriesByType(@RequestParam final CategoryType type) {
        final List<CategoryResponse> responses = categoryService.findAllCategoriesByType(type).stream()
                .map(CategoryResponse::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
