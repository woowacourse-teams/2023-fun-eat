package com.funeat.common.exception;

import com.funeat.exception.CommonErrorCode;
import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class CommonException extends GlobalException {

    public CommonException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }

    public static class NotAllowedFileExtensionException extends CommonException {
        public NotAllowedFileExtensionException(final CommonErrorCode errorCode, final String extension) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), extension));
        }
    }

    public static class S3UploadFailException extends CommonException {
        public S3UploadFailException(final CommonErrorCode errorCode) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage()));
        }
    }
}
