package com.funeat.exception;

public class ErrorCode<T> {

    private final String code;
    private final String message;
    private T info;

    public ErrorCode(final String code, final String message, final T info) {
        this.code = code;
        this.message = message;
        this.info = info;
    }

    public ErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getInfo() {
        return info;
    }
}
