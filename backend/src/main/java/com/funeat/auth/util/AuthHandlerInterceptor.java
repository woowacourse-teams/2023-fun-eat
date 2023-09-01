package com.funeat.auth.util;

import com.funeat.auth.exception.AuthErrorCode;
import com.funeat.auth.exception.AuthException.NotLoggedInException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        final HttpSession session = request.getSession(false);

        if (session == null) {
            throw new NotLoggedInException(AuthErrorCode.LOGIN_MEMBER_NOT_FOUND);
        }

        return true;
    }
}
