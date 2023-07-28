package com.funeat.tag.presentation;

import com.funeat.tag.dto.TagsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "04.Tag", description = "태그 기능")
public interface TagController {

    @Operation(summary = "전체 태그 목록 조회", description = "전체 태그 목록을 태그 타입 별로 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "전체 태그 목록 조회 성공."
    )
    @GetMapping
    ResponseEntity<List<TagsResponse>> getAllTags();
}
