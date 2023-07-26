package com.funeat.auth.application;

import com.funeat.auth.dto.UserInfoDto;
import com.funeat.auth.util.PlatformUserProvider;
import com.funeat.member.application.MemberService;
import com.funeat.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberService memberService;
    private final PlatformUserProvider platformUserProvider;

    public AuthService(final MemberService memberService, final PlatformUserProvider platformUserProvider) {
        this.memberService = memberService;
        this.platformUserProvider = platformUserProvider;
    }

    public Long loginWithKakao(final String code) {
        final UserInfoDto userInfoDto = platformUserProvider.getPlatformUser(code);
        final Member member = memberService.findOrCreateMember(userInfoDto);
        return member.getId();
    }
}
