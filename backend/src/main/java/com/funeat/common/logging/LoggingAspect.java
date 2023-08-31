package com.funeat.common.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    public void requestLogging(final JoinPoint joinPoint) throws JsonProcessingException {
        final Signature signature = joinPoint.getSignature();
        final String classAndMethodName = signature.toShortString();

        final Map<String, Object> args = getSpecificParameters(joinPoint);

        log.info("method={} args={}", classAndMethodName, objectMapper.writeValueAsString(args));
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
}
