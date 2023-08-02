package com.funeat.auth.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        if (httpMethod.equals(HttpMethod.POST) || httpMethod.equals(HttpMethod.PATCH)) {
            final HttpSession session = request.getSession();
            if (session.getAttribute("member") == null) {
                throw new IllegalArgumentException("login error");
            }
        }
        return true;
    }
}
