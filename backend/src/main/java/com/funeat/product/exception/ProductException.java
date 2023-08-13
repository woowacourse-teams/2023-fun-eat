package com.funeat.product.exception;

import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class ProductException extends GlobalException {

    public ProductException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }

    public static class ProductNotFoundException extends ProductException {
        public ProductNotFoundException(final ProductErrorCode errorCode, final Long productId) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), productId));
        }
    }
}
