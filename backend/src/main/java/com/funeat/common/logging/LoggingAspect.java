package com.funeat.common.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Aspect
@Component
public class LoggingAspect {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String IMAGE = "image";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.funeat.*.presentation.*.*(..))")
    private void allPresentation() {
    }

    @Pointcut("@annotation(com.funeat.common.logging.Logging)")
    private void logging() {
    }

    @Before("allPresentation() && logging()")
    public void requestLogging(final JoinPoint joinPoint) {
        final Class<?> clazz = joinPoint.getTarget().getClass();
        final Map<String, Object> args = getSpecificParameters(joinPoint);

        printRequestLog(clazz, args);
    }

    private String getRequestMethod(final Class<?> clazz) {
        final RequestMapping requestMapping = clazz.getDeclaredAnnotation(RequestMapping.class);
        final RequestMethod[] requestMethods = requestMapping.method();
        if (requestMethods.length > 0) {
            return requestMethods[0].name();
        }
        log.warn("[LOGGING ERROR] Request Method가 존재하지 않습니다.");
        return "";
    }

    private String getRequestUrl(final Class<?> clazz) {
        final RequestMapping requestMapping = clazz.getDeclaredAnnotation(RequestMapping.class);
        final String[] requestUrls = requestMapping.path();
        if (requestUrls.length > 0) {
            return requestUrls[0];
        }
        log.warn("[LOGGING ERROR] Request URL이 존재하지 않습니다.");
        return "";
    }

    private Map<String, Object> getSpecificParameters(final JoinPoint joinPoint) {
        final CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        final String[] parameterNames = codeSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();

        final Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            if (!parameterNames[i].equals(IMAGE)) {
                params.put(parameterNames[i], args[i]);
            }
        }

        return params;
    }

    private void printRequestLog(final Class<?> clazz, final Object value) {
        try {
            final String requestMethod = getRequestMethod(clazz);
            final String requestUrl = getRequestUrl(clazz);
            log.info("[REQUEST] {}, {}, {}", requestMethod, requestUrl, objectMapper.writeValueAsString(value));
        } catch (final JsonProcessingException e) {
            log.warn("[LOGGING ERROR] Request 로깅에 실패했습니다");
        }
    }

    @AfterReturning(value = "allPresentation() && logging()", returning = "responseEntity")
    public void requestLogging(final JoinPoint joinPoint, final ResponseEntity<?> responseEntity) {
        printResponseLog(responseEntity);
    }

    private void printResponseLog(final ResponseEntity<?> responseEntity) {
        try {
            final String responseStatus = responseEntity.getStatusCode().toString();
            log.info("[RESPONSE] {} {}", responseStatus, objectMapper.writeValueAsString(responseEntity.getBody()));
        } catch (final JsonProcessingException e) {
            log.warn("[LOGGING ERROR] Response 로깅에 실패했습니다");
        }
    }
}
