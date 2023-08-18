package com.funeat.product.exception;

import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class CategoryException extends GlobalException {

    public CategoryException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }

    public static class CategoryNotFoundException extends CategoryException {
        public CategoryNotFoundException(final CategoryErrorCode errorCode, final Long categoryId) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), categoryId));
        }
    }

    public static class CategoryTypeNotFoundException extends CategoryException {
        public CategoryTypeNotFoundException(final CategoryErrorCode errorCode, final String type) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), type));
        }
    }
}
