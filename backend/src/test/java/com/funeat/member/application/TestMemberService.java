package com.funeat.member.application;

import com.funeat.auth.dto.SignUserDto;
import com.funeat.auth.dto.UserInfoDto;
import com.funeat.common.ImageService;
import com.funeat.member.persistence.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestMemberService extends MemberService {

    public TestMemberService(final MemberRepository memberRepository, final ImageService imageService) {
        super(memberRepository, imageService);
    }

    @Override
    @Transactional
    public SignUserDto findOrCreateMember(final UserInfoDto userInfoDto) {
        return super.findOrCreateMember(userInfoDto);
    }
}
