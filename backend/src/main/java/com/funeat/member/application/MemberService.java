package com.funeat.member.application;

import static com.funeat.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.funeat.auth.dto.SignUserDto;
import com.funeat.auth.dto.UserInfoDto;
import com.funeat.common.ImageService;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import com.funeat.member.exception.MemberErrorCode;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.member.persistence.MemberRepository;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageService imageService;

    public MemberService(final MemberRepository memberRepository, final ImageService imageService) {
        this.memberRepository = memberRepository;
        this.imageService = imageService;
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
                .orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND, memberId));

        return MemberProfileResponse.toResponse(findMember);
    }

    @Transactional
    public void modify(final Long memberId, final MultipartFile image, final MemberRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND, memberId));

        final String nickname = request.getNickname();

        if (Objects.isNull(image)) {
            findMember.modifyName(nickname);
            return;
        }

        final String newImageName = imageService.getRandomImageName(image);
        findMember.modifyProfile(nickname, newImageName);
        imageService.upload(image, newImageName);
    }

    public String findPlatformId(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));

        return member.getPlatformId();
    }
}
