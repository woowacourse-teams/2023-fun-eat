package com.funeat.review.presentation;

import com.funeat.review.presentation.dto.RankingReviewsResponse;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import com.funeat.review.presentation.dto.SortingReviewsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "03.Review", description = "리뷰관련 API 입니다.")
public interface ReviewApiController {

    @Operation(summary = "리뷰 추가", description = "리뷰를 작성한다.")
    @ApiResponse(
            responseCode = "201",
            description = "리뷰 작성 성공."
    )
    @PostMapping
    ResponseEntity<Void> writeReview(@PathVariable Long productId, @RequestPart MultipartFile image,
                                     @RequestPart ReviewCreateRequest reviewRequest);

    @Operation(summary = "리뷰 좋아요", description = "리뷰에 좋아요 또는 취소를 한다.")
    @ApiResponse(
            responseCode = "204",
            description = "리뷰 좋아요(취소) 성공."
    )
    @PatchMapping
    ResponseEntity<Void> toggleLikeReview(@PathVariable Long productId, @PathVariable Long reviewId,
                                          @RequestBody ReviewFavoriteRequest request);

    @Operation(summary = "리뷰를 정렬후 조회", description = "리뷰를 정렬후 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "리뷰 정렬후 조회 성공."
    )
    @GetMapping
    ResponseEntity<SortingReviewsResponse> getSortingReviews(@PathVariable Long productId,
                                                             @PageableDefault Pageable pageable);

    @Operation(summary = "리뷰 랭킹 Top3 조회", description = "리뷰 랭킹 Top3 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "리뷰 랭킹 Top3 조회 성공."
    )
    @GetMapping
    ResponseEntity<RankingReviewsResponse> getRankingReviews();
}
