package com.funeat.tag.persistence;

import static com.funeat.fixture.TagFixture.태그_갓성비_PRICE_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.tag.domain.TagType.TASTE;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.tag.domain.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class TagRepositoryTest extends RepositoryTest {

    @Nested
    class findTagsByIdIn_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 여러_태그_아이디로_태그들을_조회_할_수_있다() {
                // given
                final var tag1 = 태그_맛있어요_TASTE_생성();
                final var tag2 = 태그_갓성비_PRICE_생성();
                final var tags = List.of(tag1, tag2);
                복수_태그_저장(tags);

                final var tagIds = 태그_아이디_변환(tags);

                // then
                final var result = tagRepository.findTagsByIdIn(tagIds);

                // when
                assertThat(result).usingRecursiveComparison().isEqualTo(tags);
            }
        }

        @Nested
        class 실패_테스트 {
        }
    }

    @Nested
    class findTagsByTagType_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 태그_타입으로_태그들을_조회할_수_있다() {
                // given
                final var tag1 = 태그_맛있어요_TASTE_생성();
                final var tag2 = 태그_갓성비_PRICE_생성();
                final var tags = List.of(tag1, tag2);
                복수_태그_저장(tags);

                final var expected = List.of(tag1);

                // when
                final var actual = tagRepository.findTagsByTagType(TASTE);

                // then
                assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
            }
        }

        @Nested
        class 실패_테스트 {
        }
    }

    private void 복수_태그_저장(final List<Tag> tags) {
        tagRepository.saveAll(tags);
    }

    private List<Long> 태그_아이디_변환(final List<Tag> tags) {
        return tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
    }
}
