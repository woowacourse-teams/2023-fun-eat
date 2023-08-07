package com.funeat.acceptance.tag;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.tag.TagSteps.전체_태그_목록_조회_요청;
import static com.funeat.tag.domain.TagType.ETC;
import static com.funeat.tag.domain.TagType.PRICE;
import static com.funeat.tag.domain.TagType.TASTE;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.domain.TagType;
import com.funeat.tag.dto.TagsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class TagAcceptanceTest extends AcceptanceTest {

    @Test
    void 전체_태그_목록을_조회할_수_있다() {
        // given
        final var tag1 = new Tag("단짠단짠", TASTE);
        final var tag2 = new Tag("매콤해요", TASTE);
        final var tag3 = new Tag("갓성비", PRICE);
        final var tag4 = new Tag("바삭바삭", ETC);
        final var tags = List.of(tag1, tag2, tag3, tag4);
        복수_태그_저장(tags);

        // when
        final var response = 전체_태그_목록_조회_요청();

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        전체_태그_목록_조회_결과를_검증한다(response, tags);
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
