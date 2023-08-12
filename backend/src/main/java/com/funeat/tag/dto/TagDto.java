package com.funeat.tag.dto;

import com.funeat.tag.domain.Tag;
import com.funeat.tag.domain.TagType;

public class TagDto {

    private final Long id;
    private final String name;
    private final TagType tagType;

    public TagDto(final Long id, final String name, final TagType tagType) {
        this.id = id;
        this.name = name;
        this.tagType = tagType;
    }

    public static TagDto toDto(final Tag tag) {
        return new TagDto(tag.getId(), tag.getName(), tag.getTagType());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TagType getTagType() {
        return tagType;
    }
}
