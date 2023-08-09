package com.funeat.member.domain;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class MemberTest {

    @Nested
    class modifyProfile_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 사용자의_닉네임과_이미지_주소를_변경할_수_있다() {
                // given
                final var member = new Member("before", "http://www.before.com", "1");

                final var expectedNickname = "after";
                final var expectedProfileImage = "http://www.after.com";

                // when
                member.modifyProfile(expectedNickname, expectedProfileImage);
                final var actualNickname = member.getNickname();
                final var actualProfileImage = member.getProfileImage();

                // then
                assertSoftly(softAssertions -> {
                    softAssertions.assertThat(actualNickname).isEqualTo(expectedNickname);
                    softAssertions.assertThat(actualProfileImage).isEqualTo(expectedProfileImage);
                });
            }
        }

        @Nested
        class 실패_테스트 {

            @Test
            void 사용자의_닉네임_변경_값이_null이면_예외를_발생해야_하지만_통과하고_있다() {
                // given
                final var member = new Member("test", "http://www.before.com", "1");

                final var expectedProfileImage = "http://www.after.com";

                // when
                member.modifyProfile(null, expectedProfileImage);
                final var actualNickname = member.getNickname();
                final var actualProfileImage = member.getProfileImage();

                // then
            }


            @Test
            void 사용자의_프로필_이미지_변경_값이_null이면_예외를_발생해야_하지만_통과하고_있다() {
                // given
                final var member = new Member("test", "http://www.before.com", "1");

                final var expectedNickname = "after";

                // when
                member.modifyProfile(expectedNickname, null);
                final var actualNickname = member.getNickname();
                final var actualProfileImage = member.getProfileImage();

                // then
            }
        }
    }
}
