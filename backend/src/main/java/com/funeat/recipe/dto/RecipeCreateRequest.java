package com.funeat.recipe.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RecipeCreateRequest {

    @NotBlank(message = "꿀조합 이름을 확인해 주세요")
    private final String title;

    @NotNull(message = "상품 ID 목록을 확인해 주세요")
    @Size(min = 1, message = "적어도 1개의 상품 ID가 필요합니다")
    private final List<Long> productIds;

    @NotBlank(message = "꿀조합 내용을 확인해 주세요")
    @Size(max = 500, message = "꿀조합 내용은 최대 500자까지 입력 가능합니다")
    private final String content;

    public RecipeCreateRequest(final String title, final List<Long> productIds, final String content) {
        this.title = title;
        this.productIds = productIds;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public String getContent() {
        return content;
    }
}
