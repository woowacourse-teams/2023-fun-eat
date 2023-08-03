package com.funeat.exception.presentation;

import com.funeat.auth.exception.LoginException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> loginExceptionHandler(final LoginException e, final HttpServletRequest request) {

        log.warn("URI: {}, 쿠키값: {}, 저장된 JSESSIONID 값: {}", request.getRequestURI(), request.getHeader("Cookie"),
                request.getSession().getId());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
