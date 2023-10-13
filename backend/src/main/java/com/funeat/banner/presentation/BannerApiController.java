package com.funeat.banner.presentation;

import com.funeat.banner.dto.BannerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "08.Banner", description = "배너 관련 API 입니다.")
public interface BannerApiController {

    @Operation(summary = "배너 전체 조회", description = "배너 전체를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "배너 전체 조회 성공."
    )
    @GetMapping
    ResponseEntity<List<BannerResponse>> getBanners();
}
