package com.funeat.recipe.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.recipe.application.RecipeService;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import java.net.URI;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RecipeApiController implements RecipeController{

    private final RecipeService recipeService;

    public RecipeApiController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(value = "/api/recipes", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> writeRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                            @RequestPart(required = false) final List<MultipartFile> images,
                                            @RequestPart final RecipeCreateRequest recipeRequest) {
        final Long recipeId = recipeService.create(loginInfo.getId(), images, recipeRequest);

        return ResponseEntity.created(URI.create("/api/recipes/" + recipeId)).build();
    }

    @GetMapping(value = "/api/recipes/{recipeId}")
    public ResponseEntity<RecipeDetailResponse> getRecipeDetail(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                @PathVariable final Long recipeId) {
        final RecipeDetailResponse response = recipeService.getRecipeDetail(loginInfo.getId(), recipeId);
        return ResponseEntity.ok(response);
    }
}
