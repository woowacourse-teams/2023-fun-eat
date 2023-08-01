package com.funeat.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.auth.dto.UserInfoDto;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberRequest;
import com.funeat.member.persistence.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ExtendWith(DataClearExtension.class)
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
            assertSoftly(softAssertions -> {
                Assertions.assertFalse(actual.isSignIn());
                assertThat(expected).containsExactly(actual.getMember());
            });
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
            assertSoftly(softAssertions -> {
                Assertions.assertTrue(actual.isSignIn());
                assertThat(expected).doesNotContain(actual.getMember());
            });
        }
    }

    @Nested
    class modify_테스트 {

        @Test
        void 닉네임과_프로필_사진이_그대로면_사용자_정보는_바뀌지_않는다() {
            // given
            final var nickname = "test";
            final var profileImage = "http://www.test.com";
            final MemberRequest request = new MemberRequest(nickname, profileImage);

            final var member = new Member(nickname, profileImage, "1");
            final var memberId = memberRepository.save(member).getId();

            final var expected = memberRepository.findById(memberId).get();
            final var expectedNickname = expected.getNickname();
            final var expectedProfileImage = expected.getProfileImage();

            // when
            memberService.modify(memberId, request);
            final var actual = memberRepository.findById(memberId).get();
            final var actualNickname = actual.getNickname();
            final var actualProfileImage = actual.getProfileImage();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualNickname).isEqualTo(expectedNickname);
                softAssertions.assertThat(actualProfileImage).isEqualTo(expectedProfileImage);
            });
        }

        @Test
        void 닉네임만_바뀌고_프로필_사진은_그대로면_닉네임만_바뀐다() {
            // given
            final var beforeNickname = "before";
            final var profileImage = "http://www.test.com";
            final var afterNickname = "after";
            final MemberRequest request = new MemberRequest(afterNickname, profileImage);

            final var member = new Member(beforeNickname, profileImage, "1");
            final var memberId = memberRepository.save(member).getId();

            final var expected = memberRepository.findById(memberId).get();
            final var expectedNickname = expected.getNickname();
            final var expectedProfileImage = expected.getProfileImage();

            // when
            memberService.modify(memberId, request);
            final var actual = memberRepository.findById(memberId).get();
            final var actualNickname = actual.getNickname();
            final var actualProfileImage = actual.getProfileImage();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualNickname).isNotEqualTo(expectedNickname);
                softAssertions.assertThat(actualProfileImage).isEqualTo(expectedProfileImage);
            });
        }

        @Test
        void 닉네임은_그대로이고_프로필_사진이_바뀌면_프로필_사진만_바뀐다() {
            // given
            final var nickname = "test";
            final var beforeProfileImage = "http://www.before.com";
            final var afterProfileImage = "http://www.after.com";

            final MemberRequest request = new MemberRequest(nickname, afterProfileImage);

            final var member = new Member(nickname, beforeProfileImage, "1");
            final var memberId = memberRepository.save(member).getId();

            final var expected = memberRepository.findById(memberId).get();
            final var expectedNickname = expected.getNickname();
            final var expectedProfileImage = expected.getProfileImage();

            // when
            memberService.modify(memberId, request);
            final var actual = memberRepository.findById(memberId).get();
            final var actualNickname = actual.getNickname();
            final var actualProfileImage = actual.getProfileImage();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualNickname).isEqualTo(expectedNickname);
                softAssertions.assertThat(actualProfileImage).isNotEqualTo(expectedProfileImage);
            });
        }

        @Test
        void 닉네임과_프로필_사진_모두_바뀌면_모두_바뀐다() {
            // given
            final var beforeNickname = "before";
            final var afterNickname = "after";
            final var beforeProfileImage = "http://www.before.com";
            final var afterProfileImage = "http://www.after.com";

            final MemberRequest request = new MemberRequest(afterNickname, afterProfileImage);

            final var member = new Member(beforeNickname, beforeProfileImage, "1");
            final var memberId = memberRepository.save(member).getId();

            final var expected = memberRepository.findById(memberId).get();
            final var expectedNickname = expected.getNickname();
            final var expectedProfileImage = expected.getProfileImage();

            // when
            memberService.modify(memberId, request);
            final var actual = memberRepository.findById(memberId).get();
            final var actualNickname = actual.getNickname();
            final var actualProfileImage = actual.getProfileImage();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualNickname).isNotEqualTo(expectedNickname);
                softAssertions.assertThat(actualProfileImage).isNotEqualTo(expectedProfileImage);
            });
        }
    }
}
