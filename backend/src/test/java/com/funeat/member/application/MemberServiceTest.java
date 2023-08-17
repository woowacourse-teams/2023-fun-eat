package com.funeat.member.application;

import static com.funeat.fixture.ImageFixture.이미지_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.auth.dto.UserInfoDto;
import com.funeat.common.ServiceTest;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.member.exception.MemberException.MemberUpdateException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

@SuppressWarnings("NonAsciiCharacters")
class MemberServiceTest extends ServiceTest {

    @Nested
    class findOrCreateMember_성공_테스트 {

        @Test
        void 이미_가입된_사용자면_가입하지_않고_반환한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var userInfoDto = new UserInfoDto(1L, "test", "www.test.com");

            final var expected = memberRepository.findAll();

            // when
            final var actual = memberService.findOrCreateMember(userInfoDto);

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actual.isSignUp())
                        .isFalse();
                softAssertions.assertThat(expected)
                        .containsExactly(actual.getMember());
            });
        }

        @Test
        void 가입되지_않은_사용자면_가입하고_반환하다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var userInfoDto = new UserInfoDto(2L, "test", "www.test.com");

            final var expected = memberRepository.findAll();

            // when
            final var actual = memberService.findOrCreateMember(userInfoDto);

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actual.isSignUp())
                        .isTrue();
                softAssertions.assertThat(expected)
                        .doesNotContain(actual.getMember());
            });
        }
    }

    @Nested
    class findOrCreateMember_실패_테스트 {

        @Test
        void platformId가_null이면_예외가_발생한다() {
            // given
            final var userInfoDto = new UserInfoDto(null, "test", "www.test.com");

            // when & then
            assertThatThrownBy(() -> memberService.findOrCreateMember(userInfoDto))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    class getMemberProfile_성공_테스트 {

        @Test
        void 사용자_정보를_확인하다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var expected = MemberProfileResponse.toResponse(member);

            // when
            final var actual = memberService.getMemberProfile(memberId);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class getMemberProfile_실패_테스트 {

        @Test
        void 존재하지_않는_사용자_정보를_조회하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var wrongMemberId = 단일_멤버_저장(member) + 1L;

            // when & then
            assertThatThrownBy(() -> memberService.getMemberProfile(wrongMemberId))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @Nested
    class modify_성공_테스트 {

        @Test
        void 닉네임과_프로필_사진이_그대로면_사용자_정보는_바뀌지_않는다() {
            // given
            final var nickname = "member1";
            final var image = new MockMultipartFile("image", "www.member1.com", "image/jpeg", new byte[]{1, 2, 3});
            final var request = new MemberRequest(nickname);

            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var expected = memberRepository.findById(memberId).get();
            final var expectedNickname = expected.getNickname();
            final var expectedProfileImage = expected.getProfileImage();

            // when
            memberService.modify(memberId, image, request);
            final var actual = memberRepository.findById(memberId).get();
            final var actualNickname = actual.getNickname();
            final var actualProfileImage = actual.getProfileImage();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualNickname)
                        .isEqualTo(expectedNickname);
                softAssertions.assertThat(actualProfileImage)
                        .isEqualTo(expectedProfileImage);
            });
        }

        @Test
        void 닉네임만_바뀌고_프로필_사진은_그대로면_닉네임만_바뀐다() {
            // given
            final var profileImage = new MockMultipartFile("image", "www.member1.com", "image/jpeg",
                    new byte[]{1, 2, 3});
            final var afterNickname = "after";
            final var request = new MemberRequest(afterNickname);

            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var expected = memberRepository.findById(memberId).get();
            final var expectedNickname = expected.getNickname();
            final var expectedProfileImage = expected.getProfileImage();

            // when
            memberService.modify(memberId, profileImage, request);
            final var actual = memberRepository.findById(memberId).get();
            final var actualNickname = actual.getNickname();
            final var actualProfileImage = actual.getProfileImage();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualNickname)
                        .isNotEqualTo(expectedNickname);
                softAssertions.assertThat(actualProfileImage)
                        .isEqualTo(expectedProfileImage);
            });
        }

        @Test
        void 닉네임은_그대로이고_프로필_사진이_바뀌면_프로필_사진만_바뀐다() {
            // given
            final var nickname = "member1";
            final var afterProfileImage = 이미지_생성();
            final var request = new MemberRequest(nickname);

            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var expected = memberRepository.findById(memberId).get();
            final var expectedNickname = expected.getNickname();
            final var expectedProfileImage = expected.getProfileImage();

            // when
            memberService.modify(memberId, afterProfileImage, request);
            final var actual = memberRepository.findById(memberId).get();
            final var actualNickname = actual.getNickname();
            final var actualProfileImage = actual.getProfileImage();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualNickname)
                        .isEqualTo(expectedNickname);
                softAssertions.assertThat(actualProfileImage)
                        .isNotEqualTo(expectedProfileImage);
            });
        }

        @Test
        void 닉네임과_프로필_사진_모두_바뀌면_모두_바뀐다() {
            // given
            final var afterNickname = "after";
            final var afterProfileImage = 이미지_생성();

            final var request = new MemberRequest(afterNickname);

            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var expected = memberRepository.findById(memberId).get();
            final var expectedNickname = expected.getNickname();
            final var expectedProfileImage = expected.getProfileImage();

            // when
            memberService.modify(memberId, afterProfileImage, request);
            final var actual = memberRepository.findById(memberId).get();
            final var actualNickname = actual.getNickname();
            final var actualProfileImage = actual.getProfileImage();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actualNickname)
                        .isNotEqualTo(expectedNickname);
                softAssertions.assertThat(actualProfileImage)
                        .isNotEqualTo(expectedProfileImage);
            });
        }
    }

    @Nested
    class modify_실패_테스트 {

        @Test
        void 존재하지않는_멤버를_수정하면_예외가_발생한다() {
            // given
            final var afterNickname = "after";
            final var afterProfileImage = 이미지_생성();

            final var member = 멤버_멤버1_생성();
            final var wrongMemberId = 단일_멤버_저장(member) + 1L;

            final var request = new MemberRequest(afterNickname);

            // when
            assertThatThrownBy(() -> memberService.modify(wrongMemberId, afterProfileImage, request))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 닉네임_수정_요청_값을_null로_설정하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var request = new MemberRequest(null);
            final var image = 이미지_생성();

            // when & then
            assertThatThrownBy(() -> memberService.modify(memberId, image, request))
                    .isInstanceOf(MemberUpdateException.class);
        }
    }
}
