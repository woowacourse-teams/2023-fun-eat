package com.funeat.member.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.Member;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class MemberRepositoryTest extends RepositoryTest {

    @Test
    void platform_id를_통해_멤버를_반환한다() {
        // given
        final var platformId = "1234";
        final var member = new Member("test", "www.test.com", platformId);
        단일_멤버_저장(member);

        // when
        final var actual = memberRepository.findByPlatformId(platformId).get();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(member);
    }

    private Long 단일_멤버_저장(final Member member) {
        return memberRepository.save(member).getId();
    }
}
