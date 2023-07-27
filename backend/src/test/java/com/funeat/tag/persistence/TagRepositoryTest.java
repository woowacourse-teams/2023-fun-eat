package com.funeat.tag.persistence;

import static com.funeat.tag.domain.TagType.ETC;
import static com.funeat.tag.domain.TagType.PRICE;
import static com.funeat.tag.domain.TagType.TASTE;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.tag.domain.Tag;
import java.util.List;
import java.util.stream.Collectors;
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
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void 여러_태그_아이디로_태그들을_조회_할_수_있다() {
        // given
        final var tag1 = 태그_추가_요청(new Tag("testTag1", ETC));
        final var tag2 = 태그_추가_요청(new Tag("testTag2", ETC));
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

    @Test
    void 태그_타입으로_태그들을_조회할_수_있다() {
        // given
        final var tag1 = 태그_추가_요청(new Tag("단짠단짠", TASTE));
        final var tag2 = 태그_추가_요청(new Tag("매콤해요", TASTE));
        final var tag3 = 태그_추가_요청(new Tag("갓성비", PRICE));
        final var expected = List.of(tag1, tag2);

        // when
        final var actual = tagRepository.findTagsByTagType(TASTE);

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private Tag 태그_추가_요청(final Tag tag) {
        return tagRepository.save(tag);
    }
}
