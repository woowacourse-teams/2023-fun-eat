package com.funeat.auth.util;

import com.funeat.auth.dto.UserInfoDto;

public interface PlatformUserProvider {

    UserInfoDto getPlatformUser(final String code);

    String getRedirectURI();

    void logout(final String platformId);
}
