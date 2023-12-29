package com.funeat.banner.application;

import static com.funeat.fixture.BannerFixture.배너1_생성;
import static com.funeat.fixture.BannerFixture.배너2_생성;
import static com.funeat.fixture.BannerFixture.배너3_생성;
import static com.funeat.fixture.BannerFixture.배너4_생성;
import static com.funeat.fixture.BannerFixture.배너5_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.banner.dto.BannerResponse;
import com.funeat.common.ServiceTest;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class BannerServiceTest extends ServiceTest {

    @Nested
    class getBanners_성공_테스트 {

        @Test
        void 배너를_아이디_내림차순으로_전체_조회한다() {
            // given
            final var 배너1 = 배너1_생성();
            final var 배너2 = 배너2_생성();
            final var 배너3 = 배너3_생성();
            final var 배너4 = 배너4_생성();
            final var 배너5 = 배너5_생성();
            복수_배너_저장(배너1, 배너2, 배너3, 배너4, 배너5);

            // when
            final var result = bannerService.getAllBanners();

            // then
            final var 배너들 = List.of(배너5, 배너4, 배너3, 배너2, 배너1);
            final var bannerResponses = 배너들.stream()
                    .map(BannerResponse::toResponse)
                    .collect(Collectors.toList());

            assertThat(result).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(bannerResponses);
        }
    }
}
