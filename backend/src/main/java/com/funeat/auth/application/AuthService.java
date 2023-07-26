package com.funeat.auth.application;

import com.funeat.auth.dto.UserInfoDto;
import com.funeat.auth.util.PlatformUserProvider;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PlatformUserProvider platformUserProvider;

    public AuthService(final MemberRepository memberRepository, final PlatformUserProvider platformUserProvider) {
        this.memberRepository = memberRepository;
        this.platformUserProvider = platformUserProvider;
    }

    public Long loginWithKakao(final String code) {
        final UserInfoDto userInfoDto = platformUserProvider.getPlatformUser(code);
        final Member member = findOrCreateMember(userInfoDto);
        return member.getId();
    }

    private Member findOrCreateMember(final UserInfoDto userInfoDto) {
        final String platformId = userInfoDto.getId().toString();

        return memberRepository.findByPlatformId(platformId).orElseGet(() -> {
            final String nickname = userInfoDto.getNickname();
            final String profileImage = userInfoDto.getProfileImageUrl();
            final Member member = new Member(nickname, profileImage, platformId);
            return memberRepository.save(member);
        });
    }
}
