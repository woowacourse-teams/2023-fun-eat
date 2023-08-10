package com.funeat.product.exception;

import org.springframework.http.HttpStatus;

public enum ProductErrorCode {

    PRODUCT_NOF_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다. 상품 id를 확인하세요.", "3001"),
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    ProductErrorCode(final HttpStatus status, final String message, final String code) {
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
