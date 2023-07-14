package com.funeat.product.presentation;

import com.funeat.product.application.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllFoodTypeCategories() {
        final List<CategoryResponse> responses = categoryService.findAllFoodTypeCategories().stream()
                .map(CategoryResponse::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
