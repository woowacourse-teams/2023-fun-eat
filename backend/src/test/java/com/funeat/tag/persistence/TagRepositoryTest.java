package com.funeat.tag.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.tag.domain.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class TagRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Test
    void 여러_태그_아이디로_태그들을_조회_할_수_있다() {
        // given
        var tags = 태그_추가_요청();
        var tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());

        // then
        List<Tag> result = tagRepository.findTagsByIdIn(tagIds);

        // when
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(tags);
    }

    private List<Tag> 태그_추가_요청() {
        final Tag testTag1 = tagRepository.save(new Tag("testTag1"));
        final Tag testTag2 = tagRepository.save(new Tag("testTag2"));

        return List.of(testTag1, testTag2);
    }
}
