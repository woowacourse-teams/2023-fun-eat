package com.funeat.product.exception;

import org.springframework.http.HttpStatus;

public enum CategoryErrorCode {

    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다. 카테고리 id를 확인하세요.", "2001"),
    CATEGORY_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리 타입입니다. 카테고리 타입을 확인하세요.", "2002"),
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    CategoryErrorCode(final HttpStatus status, final String message, final String code) {
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
