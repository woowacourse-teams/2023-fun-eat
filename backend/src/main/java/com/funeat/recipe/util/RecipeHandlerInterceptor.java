package com.funeat.recipe.util;

import com.funeat.auth.exception.AuthErrorCode;
import com.funeat.auth.exception.AuthException.NotLoggedInException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RecipeHandlerInterceptor implements HandlerInterceptor {

    private static final String GET = "GET";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (GET.equals(request.getMethod())) {
            return true;
        }

        final HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            throw new NotLoggedInException(AuthErrorCode.LOGIN_MEMBER_NOT_FOUND);
        }

        return true;
    }
}
