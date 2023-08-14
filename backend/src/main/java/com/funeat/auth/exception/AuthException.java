package com.funeat.auth.exception;

import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class AuthException extends GlobalException {

    public AuthException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }

    public static class NotLoggedInException extends AuthException {
        public NotLoggedInException(final AuthErrorCode errorCode) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage()));
        }
    }
}
