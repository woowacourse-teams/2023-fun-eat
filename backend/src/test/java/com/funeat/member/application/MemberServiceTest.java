package com.funeat.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.auth.dto.UserInfoDto;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    class findOrCreateMember_테스트 {

        @Test
        void 이미_가입된_사용자면_가입하지_않고_반환한다() {
            // given
            final var member = new Member("test", "www.test.com", "1");
            memberRepository.save(member);

            final var userInfoDto = new UserInfoDto(1L, "test", "www.test.com");
            final var expected = memberRepository.findAll();

            // when
            final var actual = memberService.findOrCreateMember(userInfoDto);

            // then
            assertThat(expected).containsExactly(actual);
        }

        @Test
        void 가입되지_않은_사용자면_가입하고_반환하다() {
            // given
            final var member = new Member("test", "www.test.com", "1");
            memberRepository.save(member);

            final var userInfoDto = new UserInfoDto(2L, "test", "www.test.com");
            final var expected = memberRepository.findAll();

            // when
            final var actual = memberService.findOrCreateMember(userInfoDto);

            // then
            assertThat(expected).doesNotContain(actual);
        }
    }
}
