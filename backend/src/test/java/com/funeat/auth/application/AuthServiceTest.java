package com.funeat.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.auth.dto.SignUserDto;
import com.funeat.common.ServiceTest;
import com.funeat.member.domain.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class AuthServiceTest extends ServiceTest {

    @Nested
    class loginWithKakao_테스트 {

        @Test
        void 카카오_로그인을_하여_유저_정보를_가져온다() {
            // given
            final var code = "test";
            final var member = new Member("test", "https://www.test.com", "1");
            final var expected = SignUserDto.of(true, member);

            // when
            final var actual = authService.loginWithKakao(code);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .ignoringFields("member.id")
                    .isEqualTo(expected);
        }
    }
}
