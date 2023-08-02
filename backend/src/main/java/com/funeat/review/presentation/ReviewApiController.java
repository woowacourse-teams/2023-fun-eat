package com.funeat.review.presentation;

import com.funeat.auth.dto.LoginRequest;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.review.application.ReviewService;
import com.funeat.review.presentation.dto.RankingReviewsResponse;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import com.funeat.review.presentation.dto.SortingReviewsResponse;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ReviewApiController implements ReviewController {

    private final ReviewService reviewService;

    public ReviewApiController(final ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(value = "/api/products/{productId}/reviews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> writeReview(@PathVariable Long productId,
                                            @AuthenticationPrincipal LoginRequest loginInfo,
                                            @RequestPart(required = false) MultipartFile image,
                                            @RequestPart ReviewCreateRequest reviewRequest) {
        reviewService.create(productId, loginInfo.getId(), image, reviewRequest);

        return ResponseEntity.created(URI.create("/api/products/" + productId)).build();
    }

    @PatchMapping("/api/products/{productId}/reviews/{reviewId}")
    public ResponseEntity<Void> toggleLikeReview(@PathVariable Long reviewId,
                                                 @AuthenticationPrincipal LoginRequest loginInfo,
                                                 @RequestBody ReviewFavoriteRequest request) {
        reviewService.likeReview(reviewId, loginInfo.getId(), request);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/api/products/{productId}/reviews")
    public ResponseEntity<SortingReviewsResponse> getSortingReviews(@PathVariable Long productId,
                                                                    @PageableDefault Pageable pageable) {
        final SortingReviewsResponse response = reviewService.sortingReviews(productId, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/ranks/reviews")
    public ResponseEntity<RankingReviewsResponse> getRankingReviews() {
        final RankingReviewsResponse response = reviewService.getTopReviews();

        return ResponseEntity.ok(response);
    }
}
