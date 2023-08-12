package com.funeat.auth.exception;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode {

    LOGIN_MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "로그인 하지 않은 회원입니다. 로그인을 해주세요.", "6001"),
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    AuthErrorCode(final HttpStatus status, final String message, final String code) {
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
