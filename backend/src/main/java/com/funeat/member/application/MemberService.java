package com.funeat.member.application;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.funeat.auth.dto.SignUserDto;
import com.funeat.auth.dto.UserInfoDto;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import com.funeat.member.exception.MemberErrorCode;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
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

    @Transactional(propagation = REQUIRES_NEW)
    public SignUserDto findOrCreateMember(final UserInfoDto userInfoDto) {
        final String platformId = userInfoDto.getId().toString();

        return memberRepository.findByPlatformId(platformId)
                .map(member -> SignUserDto.of(false, member))
                .orElseGet(() -> save(userInfoDto));
    }

    private SignUserDto save(final UserInfoDto userInfoDto) {
        final Member member = userInfoDto.toMember();
        memberRepository.save(member);

        return SignUserDto.of(true, member);
    }

    public MemberProfileResponse getMemberProfile(final Long memberId) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOF_FOUND, memberId));

        return MemberProfileResponse.toResponse(findMember);
    }

    @Transactional
    public void modify(final Long memberId, final MemberRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOF_FOUND, memberId));

        final String nickname = request.getNickname();
        final String profileImage = request.getProfileImage();

        findMember.modifyProfile(nickname, profileImage);
    }
}
