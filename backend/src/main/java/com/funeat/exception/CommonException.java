package com.funeat.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends GlobalException {

    public CommonException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }

    public static class ImageNotExistException extends CommonException {
        public ImageNotExistException(final CommonErrorCode errorCode) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage()));
        }
    }
}
