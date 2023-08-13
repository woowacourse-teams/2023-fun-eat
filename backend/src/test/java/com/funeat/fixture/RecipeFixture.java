package com.funeat.fixture;

import com.funeat.member.domain.Member;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import com.funeat.recipe.dto.RecipeCreateRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeFixture {

    public static Recipe 레시피_생성(final Member member) {
        return new Recipe("제일로 맛있는 레시피", "밥 추가, 밥 추가, 밥 추가.. 끝!!", member);
    }

    public static RecipeCreateRequest 레시피추가요청_생성(final List<Long> productIds) {
        return new RecipeCreateRequest("제일로 맛있는 레시피", productIds, "밥 추가, 밥 추가, 밥 추가.. 끝!!");
    }

    public static RecipeImage 레시피이미지_생성(final Recipe recipe) {
        return new RecipeImage("제일로 맛없는 사진", recipe);
    }
}
