package com.funeat.acceptance.tag;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.tag.TagSteps.전체_태그_목록_조회_요청;
import static com.funeat.fixture.TagFixture.태그_간식_ETC_생성;
import static com.funeat.fixture.TagFixture.태그_갓성비_PRICE_생성;
import static com.funeat.fixture.TagFixture.태그_단짠단짠_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.domain.TagType;
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
    class getAllTags_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 전체_태그_목록을_조회할_수_있다() {
                // given
                final var tag1 = 태그_맛있어요_TASTE_생성();
                final var tag2 = 태그_단짠단짠_TASTE_생성();
                final var tag3 = 태그_갓성비_PRICE_생성();
                final var tag4 = 태그_간식_ETC_생성();
                final var tags = List.of(tag1, tag2, tag3, tag4);
                복수_태그_저장(tags);

                // when
                final var response = 전체_태그_목록_조회_요청();

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                전체_태그_목록_조회_결과를_검증한다(response, tags);
            }
        }

        @Nested
        class 실패_테스트 {
        }
    }

    private void 복수_태그_저장(final List<Tag> tags) {
        tagRepository.saveAll(tags);
    }

    private void 전체_태그_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Tag> expected) {
        final var expectedByType = expected.stream()
                .collect(Collectors.groupingBy(Tag::getTagType));

        final var actual = response.jsonPath()
                .getList("", TagsResponse.class);

        assertSoftly(softAssertions -> {
            for (final var tagsResponse : actual) {
                final TagType tagType = TagType.valueOf(tagsResponse.getTagType());
                softAssertions.assertThat(tagType).isIn(expectedByType.keySet());
                softAssertions.assertThat(tagsResponse.getTags()).usingRecursiveComparison()
                        .isEqualTo(expectedByType.get(tagType));
            }
        });
    }
}
