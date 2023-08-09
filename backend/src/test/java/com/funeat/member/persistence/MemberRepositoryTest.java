package com.funeat.member.persistence;

import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.funeat.common.RepositoryTest;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class MemberRepositoryTest extends RepositoryTest {

    @Nested
    class findByPlatformId_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void platform_id를_통해_멤버를_반환한다() {
                // given
                final var platformId = "1";
                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                // when
                final var actual = memberRepository.findByPlatformId(platformId).get();

                // then
                assertThat(actual).usingRecursiveComparison().isEqualTo(member);
            }
        }

        @Nested
        class 실패_테스트 {

            @Test
            void platform_id가_잘못된_값으로_멤버를_조회할_때_예외가_발생한다() {
                // given
                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                final var wrongPlatformId = "2";

                // when, then
                assertThatThrownBy(() -> memberRepository.findByPlatformId(wrongPlatformId).get())
                        .isInstanceOf(NoSuchElementException.class);
            }
        }
    }
}
