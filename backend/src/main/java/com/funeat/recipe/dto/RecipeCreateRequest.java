package com.funeat.recipe.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RecipeCreateRequest {

    @NotBlank(message = "이름을 확인해 주세요")
    private final String name;

    @NotNull(message = "상품 ID 목록을 확인해 주세요")
    @Size(min = 1, message = "적어도 1개의 상품 ID가 필요합니다")
    private final List<Long> productIds;

    @NotBlank(message = "레시피 내용을 확인해 주세요")
    private final String content;

    public RecipeCreateRequest(final String name, final List<Long> productIds, final String content) {
        this.name = name;
        this.productIds = productIds;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public String getContent() {
        return content;
    }
}
