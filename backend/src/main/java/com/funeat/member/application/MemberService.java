package com.funeat.member.application;

import com.funeat.auth.dto.UserInfoDto;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findOrCreateMember(final UserInfoDto userInfoDto) {
        final String platformId = userInfoDto.getId().toString();

        return memberRepository.findByPlatformId(platformId)
                .orElseGet(() -> save(userInfoDto));
    }

    @Transactional
    public Member save(final UserInfoDto userInfoDto) {
        final String nickname = userInfoDto.getNickname();
        final String profileImage = userInfoDto.getProfileImageUrl();
        final String platformId = userInfoDto.getId().toString();

        final Member member = new Member(nickname, profileImage, platformId);

        return memberRepository.save(member);
    }
}
