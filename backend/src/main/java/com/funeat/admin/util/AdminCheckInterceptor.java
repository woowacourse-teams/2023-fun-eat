package com.funeat.admin.util;

import static com.funeat.auth.exception.AuthErrorCode.LOGIN_ADMIN_NOT_FOUND;

import com.funeat.admin.application.AdminChecker;
import com.funeat.admin.domain.AdminAuthInfo;
import com.funeat.auth.exception.AuthException.NotLoggedInException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminCheckInterceptor implements HandlerInterceptor {

    private final AdminChecker adminChecker;

    public AdminCheckInterceptor(final AdminChecker adminChecker) {
        this.adminChecker = adminChecker;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        final HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            throw new NotLoggedInException(LOGIN_ADMIN_NOT_FOUND);
        }

        final AdminAuthInfo adminAuthInfo = (AdminAuthInfo) session.getAttribute("authInfo");

        if (Objects.isNull(adminAuthInfo)) {
            throw new NotLoggedInException(LOGIN_ADMIN_NOT_FOUND);
        }

        return adminChecker.check(adminAuthInfo);
    }
}
