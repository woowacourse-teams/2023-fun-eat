package com.funeat.member.application;

import static com.funeat.exception.CommonErrorCode.IMAGE_VALID_ERROR_CODE;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import com.funeat.auth.dto.SignUserDto;
import com.funeat.auth.dto.UserInfoDto;
import com.funeat.common.ImageService;
import com.funeat.exception.CommonException.ImageNotExistException;
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
        checkExistImage(image);
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND, memberId));

        final String nickname = request.getNickname();
        final String profileImage = image.getOriginalFilename();

        if (findMember.isSameImage(profileImage)) {
            findMember.modifyProfile(nickname, profileImage);
            return;
        }
        findMember.modifyProfile(nickname, profileImage);
        imageService.upload(image);
    }

    private void checkExistImage(final MultipartFile image) {
        if (Objects.isNull(image)) {
            throw new ImageNotExistException(IMAGE_VALID_ERROR_CODE);
        }
    }
}
