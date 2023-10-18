package com.funeat.acceptance.banner;

import static com.funeat.acceptance.banner.BannerSteps.배너_목록_조회_요청;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.fixture.BannerFixture.배너1_생성;
import static com.funeat.fixture.BannerFixture.배너2_생성;
import static com.funeat.fixture.BannerFixture.배너3_생성;
import static com.funeat.fixture.BannerFixture.배너4_생성;
import static com.funeat.fixture.BannerFixture.배너5_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.banner.domain.Banner;
import com.funeat.banner.dto.BannerResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class BannerAcceptanceTest extends AcceptanceTest {

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
            final var 생성할_배너_리스트 = Arrays.asList(배너1, 배너2, 배너3, 배너4, 배너5);
            bannerRepository.saveAll(생성할_배너_리스트);

            // when
            final var 응답 = 배너_목록_조회_요청();

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            배너_조회_결과를_검증한다(응답, 생성할_배너_리스트);
        }
    }

    private void 배너_조회_결과를_검증한다(final ExtractableResponse<Response> response,
        final List<Banner> expected) {
        List<BannerResponse> expectedResponse = new ArrayList<>();
        for (int i = expected.size() - 1; i >= 0; i--) {
            expectedResponse.add(BannerResponse.toResponse(expected.get(i)));
        }

        final List<BannerResponse> result = response.jsonPath().getList("$", BannerResponse.class);
        assertThat(result).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expectedResponse);
    }
}
