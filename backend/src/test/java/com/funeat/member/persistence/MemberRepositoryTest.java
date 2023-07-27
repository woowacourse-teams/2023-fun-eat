package com.funeat.member.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void platform_id를_통해_유저를_반환한다() {
        // given
        final var platformId = "1234";
        final var member = new Member("test", "www.test.com", platformId);
        memberRepository.save(member);

        // when
        final var actual = memberRepository.findByPlatformId(platformId).get();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(member);
    }
}
