package com.funeat.auth.application;

import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.funeat.auth.dto.SignUserDto;
import com.funeat.common.ServiceTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class AuthServiceTest extends ServiceTest {

    @Nested
    class loginWithKakao_성공_테스트 {

        @Test
        void 카카오_로그인을_하여_멤버_정보를_가져온다() {
            // given
            final var code = "1";
            final var member = 멤버_멤버1_생성();
            final var expected = SignUserDto.of(true, member);

            // when
            final var actual = authService.loginWithKakao(code);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .ignoringFields("member.id")
                    .isEqualTo(expected);
        }
    }

    @Nested
    class logoutWithKakao_성공_테스트 {

        @Test
        void 카카오_로그인한_멤버를_로그아웃한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var memberId = member.getId();

            // when & then
            assertDoesNotThrow(() -> authService.logoutWithKakao(memberId));
        }
    }
}
