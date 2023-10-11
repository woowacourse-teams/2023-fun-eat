package com.funeat.admin.application;

import com.funeat.admin.domain.AdminAuthInfo;
import com.funeat.auth.exception.AuthErrorCode;
import com.funeat.auth.exception.AuthException.NotLoggedInException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminChecker {

    @Value("${back-office.id}")
    private String id;

    @Value("${back-office.key}")
    private String key;


    public boolean check(final AdminAuthInfo adminAuthInfo) {
        if (!id.equals(adminAuthInfo.getId())) {
            throw new NotLoggedInException(AuthErrorCode.LOGIN_ADMIN_NOT_FOUND);
        }

        if (!key.equals(adminAuthInfo.getKey())) {
            throw new NotLoggedInException(AuthErrorCode.LOGIN_ADMIN_NOT_FOUND);
        }

        return true;
    }
}
