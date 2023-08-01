package com.funeat.member.application;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.funeat.auth.dto.SignUserDto;
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

    public SignUserDto findOrCreateMember(final UserInfoDto userInfoDto) {
        final String platformId = userInfoDto.getId().toString();

        return memberRepository.findByPlatformId(platformId)
                .map(member -> SignUserDto.of(false, member))
                .orElseGet(() -> save(userInfoDto));
    }

    @Transactional(propagation = REQUIRES_NEW)
    public SignUserDto save(final UserInfoDto userInfoDto) {
        final Member member = userInfoDto.toMember();
        memberRepository.save(member);

        return SignUserDto.of(true, member);
    }
}
