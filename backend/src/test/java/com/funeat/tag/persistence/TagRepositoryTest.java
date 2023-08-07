package com.funeat.tag.persistence;

import static com.funeat.tag.domain.TagType.ETC;
import static com.funeat.tag.domain.TagType.PRICE;
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

        @Test
        void 여러_태그_아이디로_태그들을_조회_할_수_있다() {
            // given
            final var tag1 = 단일_태그_저장(new Tag("testTag1", ETC));
            final var tag2 = 단일_태그_저장(new Tag("testTag2", ETC));
            final var tags = List.of(tag1, tag2);
            final var tagIds = tags.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());

            // then
            final var result = tagRepository.findTagsByIdIn(tagIds);

            // when
            assertThat(result).usingRecursiveComparison()
                    .isEqualTo(tags);
        }
    }

    @Nested
    class findTagsByTagType_테스트 {

        @Test
        void 태그_타입으로_태그들을_조회할_수_있다() {
            // given
            final var tag1 = 단일_태그_저장(new Tag("단짠단짠", TASTE));
            final var tag2 = 단일_태그_저장(new Tag("매콤해요", TASTE));
            final var tag3 = 단일_태그_저장(new Tag("갓성비", PRICE));
            final var expected = List.of(tag1, tag2);

            // when
            final var actual = tagRepository.findTagsByTagType(TASTE);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    private Tag 단일_태그_저장(final Tag tag) {
        return tagRepository.save(tag);
    }
}
