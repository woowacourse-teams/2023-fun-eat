package com.funeat.fixture;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeFavoriteRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeFixture {

    public static Recipe 레시피_생성(final Member member) {
        return new Recipe("The most delicious recipes", "More rice, more rice, more rice.. Done!!", member);
    }

    public static Recipe 레시피_생성(final Member member, final Long favoriteCount) {
        return new Recipe("The most delicious recipes", "More rice, more rice, more rice.. Done!!", member, favoriteCount);
    }

    public static RecipeFavorite 레시피_좋아요_생성(final Member member, final Recipe recipe, final Boolean favorite) {
        return new RecipeFavorite(member, recipe, favorite);
    }

    public static RecipeCreateRequest 레시피추가요청_생성(final List<Long> productIds) {
        return new RecipeCreateRequest("The most delicious recipes", productIds, "More rice, more rice, more rice.. Done!!");
    }

    public static RecipeFavoriteRequest 레시피좋아요요청_생성(final Boolean favorite) {
        return new RecipeFavoriteRequest(favorite);
    }

    public static RecipeImage 레시피이미지_생성(final Recipe recipe) {
        return new RecipeImage("The most delicious image", recipe);
    }
}
