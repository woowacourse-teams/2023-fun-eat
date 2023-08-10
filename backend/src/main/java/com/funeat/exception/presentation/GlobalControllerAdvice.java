package com.funeat.exception.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final ErrorCode<String> errorCode = new ErrorCode<>("0000",
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "알 수 없는 에러입니다.");

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;

    public GlobalControllerAdvice(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> handleGlobalException(final GlobalException e, final HttpServletRequest request)
            throws JsonProcessingException {
        log.warn("request = {} code = {} message = {} info = {}", request.getRequestURI(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(), objectMapper.writeValueAsString(e.getErrorCode().getInfo()));

        return ResponseEntity.status(e.getStatus()).body(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServerException(final Exception e) {
        log.error("code = {} ", errorCode.getCode(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorCode);
    }
}
