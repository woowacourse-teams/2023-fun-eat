package com.funeat.admin.presentation;

import com.funeat.admin.application.AdminService;
import com.funeat.admin.dto.AdminProductSearchResponse;
import com.funeat.admin.dto.AdminReviewSearchResponse;
import com.funeat.admin.dto.ProductCreateRequest;
import com.funeat.admin.dto.ProductSearchCondition;
import com.funeat.admin.dto.ProductUpdateRequest;
import com.funeat.admin.dto.ReviewSearchCondition;
import com.funeat.product.dto.CategoryResponse;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@RequestBody final ProductCreateRequest request) {
        final Long productId = adminService.addProduct(request);

        return ResponseEntity.created(URI.create("/api/products/" + productId)).build();
    }

    @GetMapping("/products")
    public ResponseEntity<AdminProductSearchResponse> getSearchProducts(
            @ModelAttribute final ProductSearchCondition condition,
            @PageableDefault final Pageable pageable) {
        final AdminProductSearchResponse response = adminService.getSearchProducts(condition, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long productId,
                                              @RequestBody final ProductUpdateRequest request) {
        adminService.updateProduct(productId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        final List<CategoryResponse> responses = adminService.getAllCategories();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/reviews")
    public ResponseEntity<AdminReviewSearchResponse> getSearchReviews(
            @ModelAttribute final ReviewSearchCondition condition,
            @PageableDefault final Pageable pageable) {
        final AdminReviewSearchResponse responses = adminService.getSearchReviews(condition, pageable);
        return ResponseEntity.ok(responses);
    }
}
