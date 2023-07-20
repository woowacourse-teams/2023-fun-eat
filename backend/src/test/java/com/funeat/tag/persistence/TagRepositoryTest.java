package com.funeat.tag.persistence;

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
        final var tags = 태그_추가_요청();
        final var tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());

        // then
        final var result = tagRepository.findTagsByIdIn(tagIds);

        // when
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(tags);
    }

    private List<Tag> 태그_추가_요청() {
        final var testTag1 = tagRepository.save(new Tag("testTag1"));
        final var testTag2 = tagRepository.save(new Tag("testTag2"));

        return List.of(testTag1, testTag2);
    }
}
