package com.funeat.fixture;

import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberRequest;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {

    public static final Long 멤버1 = 1L;
    public static final Long 멤버2 = 2L;
    public static final Long 멤버3 = 3L;


    public static Member 멤버_멤버1_생성() {
        return new Member("member1", "www.member1.com", "1");
    }

    public static Member 멤버_멤버2_생성() {
        return new Member("member2", "www.member2.com", "2");
    }

    public static Member 멤버_멤버3_생성() {
        return new Member("member3", "www.member3.com", "3");
    }

    public static MemberRequest 유저닉네임수정요청_생성(final String modifyNickname) {
        return new MemberRequest(modifyNickname);
    }
}
