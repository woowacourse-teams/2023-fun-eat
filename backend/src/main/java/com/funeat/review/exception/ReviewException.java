package com.funeat.review.exception;

import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class ReviewException extends GlobalException {

    public ReviewException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }

    public static class ReviewNotFoundException extends ReviewException {
        public ReviewNotFoundException(final ReviewErrorCode errorCode, final Long reviewId) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), reviewId));
        }
    }

    public static class ReviewSortingOptionNotFoundException extends ReviewException {
        public ReviewSortingOptionNotFoundException(final ReviewErrorCode errorCode, final Long productId) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), productId));
        }
    }
}
