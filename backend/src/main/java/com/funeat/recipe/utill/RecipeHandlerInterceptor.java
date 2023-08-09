package com.funeat.recipe.utill;

import com.funeat.auth.exception.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RecipeHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("GET".equals(request.getMethod())) {
            return true;
        }
        final HttpSession session = request.getSession();
        if (session.getAttribute("member") == null) {
            throw new LoginException("login error");
        }
        return true;
    }
}
