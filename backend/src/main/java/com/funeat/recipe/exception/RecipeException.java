package com.funeat.recipe.exception;

import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class RecipeException extends GlobalException {

    public RecipeException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }

    public static class RecipeNotFoundException extends RecipeException {
        public RecipeNotFoundException(final RecipeErrorCode errorCode, final Long RecipeId) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), RecipeId));
        }
    }
}
