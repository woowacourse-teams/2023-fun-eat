package com.funeat.recipe.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.recipe.application.RecipeService;
import com.funeat.recipe.dto.RankingRecipesResponse;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.dto.RecipeFavoriteRequest;
import com.funeat.recipe.dto.SearchRecipeResultsResponse;
import com.funeat.recipe.dto.SortingRecipesResponse;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeApiController implements RecipeController {

    private final RecipeService recipeService;

    public RecipeApiController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipes")
    public ResponseEntity<Void> writeRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                            @RequestBody @Valid final RecipeCreateRequest recipeRequest) {
        final Long recipeId = recipeService.create(loginInfo.getId(), recipeRequest);

        return ResponseEntity.created(URI.create("/api/recipes/" + recipeId)).build();
    }

    @GetMapping(value = "/api/recipes/{recipeId}")
    public ResponseEntity<RecipeDetailResponse> getRecipeDetail(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                @PathVariable final Long recipeId) {
        final RecipeDetailResponse response = recipeService.getRecipeDetail(loginInfo.getId(), recipeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/api/recipes")
    public ResponseEntity<SortingRecipesResponse> getSortingRecipes(@PageableDefault final Pageable pageable) {
        final SortingRecipesResponse response = recipeService.getSortingRecipes(pageable);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/api/recipes/{recipeId}")
    public ResponseEntity<Void> likeRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                           @PathVariable final Long recipeId,
                                           @RequestBody @Valid final RecipeFavoriteRequest request) {
        recipeService.likeRecipe(loginInfo.getId(), recipeId, request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/ranks/recipes")
    public ResponseEntity<RankingRecipesResponse> getRankingRecipes() {
        final RankingRecipesResponse response = recipeService.getTop3Recipes();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/search/recipes/results")
    public ResponseEntity<SearchRecipeResultsResponse> getSearchResults(@RequestParam final String query,
                                                                        @PageableDefault final Pageable pageable) {
        final PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        final SearchRecipeResultsResponse response = recipeService.getSearchResults(query, pageRequest);

        return ResponseEntity.ok(response);
    }
}
