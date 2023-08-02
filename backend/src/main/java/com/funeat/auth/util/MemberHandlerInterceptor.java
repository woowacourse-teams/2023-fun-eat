package com.funeat.auth.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MemberHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final HttpSession session = request.getSession();
        System.out.println(
                "request.getSession().getAttribute(\"member\"))" + request.getSession().getAttribute("member"));
        System.out.println("request.getHeader(\"Cookie\")" + request.getHeader("Cookie"));
        System.out.println("request.getRequestURL().toString()" + request.getRequestURL().toString());
        System.out.println("session.getId() = " + session.getId());
        if (session.getAttribute("member") == null) {
            throw new IllegalArgumentException("login error");
        }
        return true;
    }
}
