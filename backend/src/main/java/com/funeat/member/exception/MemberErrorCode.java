package com.funeat.member.exception;

import org.springframework.http.HttpStatus;

public enum MemberErrorCode {

    MEMBER_NOF_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다. 회원 id를 확인하세요.", "1001"),
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    MemberErrorCode(final HttpStatus status, final String message, final String code) {
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
