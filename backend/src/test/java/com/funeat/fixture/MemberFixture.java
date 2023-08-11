package com.funeat.fixture;

import com.funeat.member.domain.Member;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {

    public static Member 멤버_멤버1_생성() {
        return new Member("member1", "www.member1.com", "1");
    }

    public static Member 멤버_멤버2_생성() {
        return new Member("member2", "www.member2.com", "2");
    }

    public static Member 멤버_멤버3_생성() {
        return new Member("member3", "www.member3.com", "3");
    }
}
