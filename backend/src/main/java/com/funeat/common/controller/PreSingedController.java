package com.funeat.common.controller;

import com.funeat.common.dto.S3UrlRequest;
import com.funeat.common.dto.S3UrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PreSingedController {

    @Operation(summary = "S3 업로드 URL 요청", description = "S3 업로드 URL 요청한다.")
    @ApiResponse(
            responseCode = "201",
            description = "업로드 URL 요청 성공."
    )
    @PostMapping
    ResponseEntity<S3UrlResponse> getPreSingedUrl(@RequestBody final S3UrlRequest request);
}
