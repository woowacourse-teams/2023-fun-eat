package com.funeat.tag.dto;

import java.util.List;

public class TagsResponse {

    private final String tagType;
    private final List<TagDto> tags;

    public TagsResponse(final String tagType, final List<TagDto> tags) {
        this.tagType = tagType;
        this.tags = tags;
    }

    public String getTagType() {
        return tagType;
    }

    public List<TagDto> getTags() {
        return tags;
    }
}
