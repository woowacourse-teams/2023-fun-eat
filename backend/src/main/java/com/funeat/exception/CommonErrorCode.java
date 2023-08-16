package com.funeat.exception;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode {

    UNKNOWN_SERVER_ERROR_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러입니다.", "0000"),
    REQUEST_VALID_ERROR_CODE(HttpStatus.BAD_REQUEST, "요청을 다시 확인해주세요.", "0001"),
    IMAGE_VALID_ERROR_CODE(HttpStatus.BAD_REQUEST, "이미지를 다시 확인해주세요.", "0002"),
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    CommonErrorCode(final HttpStatus status, final String message, final String code) {
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
