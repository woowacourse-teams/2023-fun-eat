package com.funeat.recipe.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "07. Recipe", description = "꿀조합 관련 API 입니다.")
public interface RecipeController {

    @Operation(summary = "꿀조합 추가", description = "꿀조합을 작성한다.")
    @ApiResponse(
            responseCode = "201",
            description = "꿀조합 작성 성공."
    )
    @PostMapping
    ResponseEntity<Void> writeRecipe(@AuthenticationPrincipal LoginInfo loginInfo,
                                     @RequestPart List<MultipartFile> images,
                                     @RequestPart RecipeCreateRequest recipeRequest);

    @Operation(summary = "꿀조합 상세 조회", description = "꿀조합의 상세 정보를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "꿀조합 상세 조회 성공."
    )
    @GetMapping
    ResponseEntity<RecipeDetailResponse> getRecipeDetail(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                @PathVariable final Long recipeId);
}
