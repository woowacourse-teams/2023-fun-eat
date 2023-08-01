package com.funeat.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class MemberTest {

    @Test
    void 사용자의_닉네임을_변경할_수_있다() {
        // given
        final var member = new Member("test", "http://www.test.com", "1");
        final var expected = "hello";

        // when
        member.modifyNickname(expected);
        final var actual = member.getNickname();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 사용자의_프로필_이미지_주소를_변경할_수_있다() {
        // given
        final var member = new Member("test", "http://www.test.com", "1");
        final var expected = "http://www.hello.com";

        // when
        member.modifyProfileImage(expected);
        final var actual = member.getProfileImage();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
