package com.funeat.common.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {

    private static final List<String> excludeNames = Arrays.asList("image", "request");

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.funeat.*.presentation.*.*(..))")
    private void allPresentation() {
    }

    @Pointcut("@annotation(com.funeat.common.logging.Logging)")
    private void logging() {
    }

    @Before("allPresentation() && logging()")
    public void requestLogging(final JoinPoint joinPoint) {
        final HttpServletRequest request = getRequest();
        final Map<String, Object> args = getSpecificParameters(joinPoint);

        printRequestLog(request, args);
    }

    private HttpServletRequest getRequest() {
        final ServletRequestAttributes servletRequestAttributes
                = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    private Map<String, Object> getSpecificParameters(final JoinPoint joinPoint) {
        final CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        final String[] parameterNames = codeSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();

        final Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            if (!excludeNames.contains(parameterNames[i])) {
                params.put(parameterNames[i], args[i]);
            }
        }

        return params;
    }

    private void printRequestLog(final HttpServletRequest request, final Object value) {
        try {
            log.info("[REQUEST {}] [PATH {}] {}",
                    request.getMethod(), request.getRequestURI(), objectMapper.writeValueAsString(value));
        } catch (final JsonProcessingException e) {
            log.warn("[LOGGING ERROR] Request 로깅에 실패했습니다");
        }
    }

    @AfterReturning(value = "allPresentation() && logging()", returning = "responseEntity")
    public void requestLogging(final ResponseEntity<?> responseEntity) {
        printResponseLog(responseEntity);
    }

    private void printResponseLog(final ResponseEntity<?> responseEntity) {
        try {
            final String responseStatus = responseEntity.getStatusCode().toString();
            log.info("[RESPONSE {}] {}", responseStatus, objectMapper.writeValueAsString(responseEntity.getBody()));
        } catch (final JsonProcessingException e) {
            log.warn("[LOGGING ERROR] Response 로깅에 실패했습니다");
        }
    }
}
