package com.funeat.acceptance.common;

import com.funeat.auth.dto.UserInfoDto;
import com.funeat.auth.util.PlatformUserProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestPlatformUserProvider implements PlatformUserProvider {

    @Override
    public UserInfoDto getPlatformUser(final String code) {
        return new UserInfoDto(1L, "test", "https://www.test.com");
    }

    @Override
    public String getRedirectURI() {
        return "https://www.test.com";
    }
}
