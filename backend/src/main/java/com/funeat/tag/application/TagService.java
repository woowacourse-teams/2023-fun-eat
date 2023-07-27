package com.funeat.tag.application;

import com.funeat.tag.domain.TagType;
import com.funeat.tag.dto.TagDto;
import com.funeat.tag.dto.TagsResponse;
import com.funeat.tag.persistence.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagsResponse> getAllTags() {
        final List<TagsResponse> responses = new ArrayList<>();
        for (final TagType tagType : TagType.values()) {
            getTagsByTagType(responses, tagType);
        }
        return responses;
    }

    private void getTagsByTagType(final List<TagsResponse> responses, final TagType tagType) {
        final List<TagDto> tags = tagRepository.findTagsByTagType(tagType).stream()
                .map(TagDto::toDto)
                .collect(Collectors.toList());
        final TagsResponse tagsResponse = TagsResponse.toResponse(tagType.name(), tags);
        responses.add(tagsResponse);
    }
}
