package com.funeat.member.exception;

import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class MemberException extends GlobalException {

    public MemberException(final HttpStatus status, final ErrorCode errorCode) {
        super(status, errorCode);
    }

    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException(final MemberErrorCode errorCode, final Long memberId) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), memberId));
        }
    }

    public static class MemberUpdateException extends MemberException {
        public MemberUpdateException(final MemberErrorCode errorCode) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage()));
        }
    }

    public static class MemberDuplicateFavoriteException extends MemberException {
        public MemberDuplicateFavoriteException(final MemberErrorCode errorCode, final Long memberId) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), memberId));
        }
    }
}
