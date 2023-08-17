package com.funeat.auth.application;

import com.funeat.auth.dto.SignUserDto;
import com.funeat.auth.dto.UserInfoDto;
import com.funeat.auth.util.PlatformUserProvider;
import com.funeat.member.application.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final MemberService memberService;
    private final PlatformUserProvider platformUserProvider;

    public AuthService(final MemberService memberService, final PlatformUserProvider platformUserProvider) {
        this.memberService = memberService;
        this.platformUserProvider = platformUserProvider;
    }

    public SignUserDto loginWithKakao(final String code) {
        final UserInfoDto userInfoDto = platformUserProvider.getPlatformUser(code);
        return memberService.findOrCreateMember(userInfoDto);
    }

    public String getLoginRedirectUri() {
        return platformUserProvider.getRedirectURI();
    }

    public void logoutWithKakao(final Long memberId) {
        final String platformId = memberService.findPlatformId(memberId);
        platformUserProvider.logout(platformId);
    }
}
