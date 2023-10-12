package com.funeat.fixture;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeFavoriteRequest;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeFixture {

    public static final Long 레시피 = 1L;
    public static final Long 존재하지_않는_레시피 = 99999L;
    public static final Long 레시피1 = 1L;
    public static final Long 레시피2 = 2L;
    public static final Long 레시피3 = 3L;
    public static final Long 레시피4 = 4L;

    public static final boolean 좋아요O = true;
    public static final boolean 좋아요X = false;

    public static final String 레시피_제목 = "The most delicious recipes";
    public static final String 레시피_본문 = "More rice, more rice, more rice.. Done!!";


    public static Recipe 레시피_생성(final Member member) {
        return new Recipe("The most delicious recipes", "More rice, more rice, more rice.. Done!!", member);
    }

    public static Recipe 레시피_생성(final Member member, final Long favoriteCount) {
        return new Recipe("The most delicious recipes", "More rice, more rice, more rice.. Done!!", member, favoriteCount);
    }

    public static Recipe 레시피_생성(final Member member, final Long favoriteCount, final LocalDateTime createdAt) {
        return new Recipe("The most delicious recipes", "More rice, more rice, more rice.. Done!!",
                member, favoriteCount, createdAt);
    }

    public static RecipeFavorite 레시피_좋아요_생성(final Member member, final Recipe recipe, final Boolean favorite) {
        return new RecipeFavorite(member, recipe, favorite);
    }

    public static RecipeCreateRequest 레시피추가요청_생성(final String title, final List<Long> productIds, final String content) {
        return new RecipeCreateRequest(title, productIds, content);
    }

    public static RecipeCreateRequest 레시피추가요청_생성(final Long... productIds) {
        return new RecipeCreateRequest("The most delicious recipes", List.of(productIds), "More rice, more rice, more rice.. Done!!");
    }

    public static RecipeCreateRequest 레시피추가요청_생성(final List<Long> productIds) {
        return new RecipeCreateRequest("The most delicious recipes", productIds, "More rice, more rice, more rice.. Done!!");
    }

    public static RecipeFavoriteRequest 레시피좋아요요청_생성(final Boolean favorite) {
        return new RecipeFavoriteRequest(favorite);
    }

    public static RecipeImage 레시피이미지_생성(final Recipe recipe) {
        return new RecipeImage("제일로 맛없는 사진", recipe);
    }
}
