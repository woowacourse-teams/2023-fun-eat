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

    public static final Long 레시피 = 1L;
    public static final Long 존재하지_않는_레시피 = 99999L;
    public static final Long 레시피1 = 1L;
    public static final Long 레시피2 = 2L;
    public static final Long 레시피3 = 3L;
    public static final Long 레시피4 = 4L;

    public static final boolean 좋아요O = true;
    public static final boolean 좋아요X = false;


    public static Recipe 레시피_생성(final Member member) {
        return new Recipe("제일로 맛있는 레시피", "밥 추가, 밥 추가, 밥 추가.. 끝!!", member);
    }

    public static Recipe 레시피_생성(final Member member, final Long favoriteCount) {
        return new Recipe("제일로 맛있는 레시피", "밥 추가, 밥 추가, 밥 추가.. 끝!!", member, favoriteCount);
    }

    public static RecipeFavorite 레시피_좋아요_생성(final Member member, final Recipe recipe, final Boolean favorite) {
        return new RecipeFavorite(member, recipe, favorite);
    }

    public static RecipeCreateRequest 레시피추가요청_생성(final String title, final List<Long> productIds, final String content) {
        return new RecipeCreateRequest(title, productIds, content);
    }

    public static RecipeCreateRequest 레시피추가요청_생성(final Long... productIds) {
        return new RecipeCreateRequest("제일로 맛있는 레시피", List.of(productIds), "밥 추가, 밥 추가, 밥 추가.. 끝!!");
    }

    public static RecipeCreateRequest 레시피추가요청_생성(final List<Long> productIds) {
        return new RecipeCreateRequest("제일로 맛있는 레시피", productIds, "밥 추가, 밥 추가, 밥 추가.. 끝!!");
    }

    public static RecipeFavoriteRequest 레시피좋아요요청_생성(final Boolean favorite) {
        return new RecipeFavoriteRequest(favorite);
    }

    public static RecipeImage 레시피이미지_생성(final Recipe recipe) {
        return new RecipeImage("제일로 맛없는 사진", recipe);
    }
}
