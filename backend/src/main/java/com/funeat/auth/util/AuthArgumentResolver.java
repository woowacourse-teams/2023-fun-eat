package com.funeat.auth.util;

import com.funeat.auth.dto.LoginInfo;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        System.out.println("request.getSession().getAttribute(\"member\"))"+ request.getSession().getAttribute("member"));
        System.out.println("request.getHeader(\"Cookie\")"+ request.getHeader("Cookie"));
        System.out.println("request.getRequestURL().toString()"+request.getRequestURL().toString());

        final HttpSession session = Objects.requireNonNull(request).getSession();
        final String id = String.valueOf(session.getAttribute("member"));

        return new LoginInfo(Long.valueOf(id));
    }
}
