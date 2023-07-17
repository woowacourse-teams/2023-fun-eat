package com.funeat.review.presentation;

import com.funeat.review.application.ReviewService;
import com.funeat.review.presentation.dto.ReviewCreateRequest;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.funeat.review.application.ReviewService;
import com.funeat.review.presentation.dto.SortingReviewDto;
import com.funeat.review.presentation.dto.SortingReviewsPageDto;
import com.funeat.review.presentation.dto.SortingReviewsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(final ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(value = "/api/products/{productId}/reviews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> writeReview(@PathVariable Long productId,
                                            @RequestPart MultipartFile image,
                                            @RequestPart ReviewCreateRequest reviewRequest) {
        reviewService.create(productId, image, reviewRequest);

        return ResponseEntity.created(URI.create("/api/products/" + productId)).build();
    }

    @GetMapping(value = "/api/products/{productId}/reviews")
    public ResponseEntity<SortingReviewsResponse> getSortingReviews(@PathVariable Long productId,
                                                                    @PageableDefault Pageable pageable) {
        final SortingReviewsResponse response = reviewService.sortingReviews(productId, pageable);

        return ResponseEntity.ok(response);
    }
}
