package com.funeat.exception.presentation;

import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.exception.CommonErrorCode.UNKNOWN_SERVER_ERROR_CODE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funeat.exception.ErrorCode;
import com.funeat.exception.GlobalException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final String ERROR_MESSAGE_DELIMITER = ", ";
    private static final String RESPONSE_DELIMITER = ". ";

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;

    public GlobalControllerAdvice(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleParamValidationException(final Exception e, final HttpServletRequest request) {
        log.warn("{} = {}, code = {} message = {}", request.getMethod(), request.getRequestURI(),
                REQUEST_VALID_ERROR_CODE.getCode(), e.getMessage());

        final ErrorCode<?> errorCode = new ErrorCode<>(REQUEST_VALID_ERROR_CODE.getCode(),
                REQUEST_VALID_ERROR_CODE.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleParamValidationException(final MethodArgumentNotValidException e,
                                                            final HttpServletRequest request) {
        final String filedErrorLogMessages = getMethodArgumentExceptionLogMessage(e);

        final String errorMessage = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(ERROR_MESSAGE_DELIMITER));

        final String responseErrorMessage = errorMessage + RESPONSE_DELIMITER + REQUEST_VALID_ERROR_CODE.getMessage();

        final ErrorCode<?> errorCode = new ErrorCode<>(REQUEST_VALID_ERROR_CODE.getCode(), responseErrorMessage);

        log.warn("{} = {}, message = {} ", request.getMethod(), request.getRequestURI(),
                filedErrorLogMessages);
        return ResponseEntity.status(REQUEST_VALID_ERROR_CODE.getStatus()).body(errorCode);
    }

    private static String getMethodArgumentExceptionLogMessage(final MethodArgumentNotValidException e) {
        final String filedErrorMessages = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(ERROR_MESSAGE_DELIMITER));

        return filedErrorMessages + " 요청 실패";
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> handleGlobalException(final GlobalException e, final HttpServletRequest request)
            throws JsonProcessingException {
        final String exceptionSource = getExceptionSource(e);
        log.warn("source = {} , {} = {} code = {} message = {} info = {}", exceptionSource, request.getMethod(),
                request.getRequestURI(), e.getErrorCode().getCode(), e.getErrorCode().getMessage(),
                objectMapper.writeValueAsString(e.getErrorCode().getInfo()));

        final ErrorCode<?> errorCode = new ErrorCode<>(e.getErrorCode().getCode(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(errorCode);
    }

    private String getExceptionSource(final Exception e) {
        final StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace.length > 0) {
            return stackTrace[0].toString();
        }
        return "Unknown location";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServerException(final Exception e) {
        log.error("", e);

        final ErrorCode<?> errorCode = new ErrorCode<>(UNKNOWN_SERVER_ERROR_CODE.getCode(),
                UNKNOWN_SERVER_ERROR_CODE.getMessage());
        return ResponseEntity.status(UNKNOWN_SERVER_ERROR_CODE.getStatus()).body(errorCode);
    }
}
