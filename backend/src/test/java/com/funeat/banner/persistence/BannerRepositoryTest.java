package com.funeat.banner.persistence;

import static com.funeat.fixture.BannerFixture.배너1_생성;
import static com.funeat.fixture.BannerFixture.배너2_생성;
import static com.funeat.fixture.BannerFixture.배너3_생성;
import static com.funeat.fixture.BannerFixture.배너4_생성;
import static com.funeat.fixture.BannerFixture.배너5_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class BannerRepositoryTest extends RepositoryTest {

    @Nested
    class findAllByOrderByIdDesc_성공_테스트 {

        @Test
        void 배너는_아이디가_내림차순으로_조회된다() {
            // given
            final var 배너1 = 배너1_생성();
            final var 배너2 = 배너2_생성();
            final var 배너3 = 배너3_생성();
            final var 배너4 = 배너4_생성();
            final var 배너5 = 배너5_생성();
            복수_배너_저장(배너1, 배너2, 배너3, 배너4, 배너5);

            // when
            final var bannersOrderByIdDesc = bannerRepository.findAllByOrderByIdDesc();

            // then
            assertThat(bannersOrderByIdDesc).usingRecursiveComparison()
                    .isEqualTo(List.of(배너5, 배너4, 배너3, 배너2, 배너1));
        }
    }
}
