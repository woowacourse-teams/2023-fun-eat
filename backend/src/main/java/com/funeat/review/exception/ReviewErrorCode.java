package com.funeat.review.exception;

import org.springframework.http.HttpStatus;

public enum ReviewErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다. 리뷰 id를 확인하세요.", "3001"),
    REVIEW_SORTING_OPTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 정렬 옵션입니다. 정렬 옵션과 정렬하려는 상품 id를 확인하세요.", "3002"),
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    ReviewErrorCode(final HttpStatus status, final String message, final String code) {
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
