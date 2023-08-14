package com.funeat.recipe.exception;

import org.springframework.http.HttpStatus;

public enum RecipeErrorCode {

    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 꿀조합입니다. 꿀조합 id를 확인하세요.", "7001"),
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    RecipeErrorCode(final HttpStatus status, final String message, final String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
