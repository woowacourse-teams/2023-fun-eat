package com.funeat.acceptance.tag;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.tag.TagSteps.전체_태그_목록_조회_요청;
import static com.funeat.fixture.TagFixture.태그_간식_ETC_생성;
import static com.funeat.fixture.TagFixture.태그_갓성비_PRICE_생성;
import static com.funeat.fixture.TagFixture.태그_단짠단짠_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.tag.dto.TagDto;
import com.funeat.tag.dto.TagsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class TagAcceptanceTest extends AcceptanceTest {

    @Nested
    class getAllTags_성공_테스트 {

        @Test
        void 전체_태그_목록을_조회할_수_있다() {
            // given
            단일_태그_저장(태그_맛있어요_TASTE_생성());
            단일_태그_저장(태그_단짠단짠_TASTE_생성());
            단일_태그_저장(태그_갓성비_PRICE_생성());
            단일_태그_저장(태그_간식_ETC_생성());

            // when
            final var response = 전체_태그_목록_조회_요청();

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            전체_태그_목록_조회_결과를_검증한다(response, List.of(1L, 2L, 3L, 4L));
        }
    }

    private void 전체_태그_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> tagIds) {
        final var actual = response.jsonPath()
                .getList("", TagsResponse.class);

        final var actualTagIds = actual.stream()
                .flatMap(tagsResponse -> tagsResponse.getTags().stream())
                .map(TagDto::getId)
                .collect(Collectors.toList());

        assertThat(actualTagIds).containsExactlyElementsOf(tagIds);
    }
}
