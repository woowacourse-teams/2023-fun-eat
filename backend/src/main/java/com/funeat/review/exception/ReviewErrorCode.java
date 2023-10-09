package com.funeat.review.exception;

import org.springframework.http.HttpStatus;

public enum ReviewErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다. 리뷰 id를 확인하세요.", "3001"),
    NOT_AUTHOR_OF_REVIEW(HttpStatus.BAD_REQUEST, "리뷰의 작성자가 아닙니다.", "3002")
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
