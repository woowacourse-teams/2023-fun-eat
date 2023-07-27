package com.funeat.tag.dto;

import com.funeat.tag.domain.Tag;

public class TagDto {

    private final Long id;
    private final String name;

    public TagDto(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static TagDto toDto(final Tag tag) {
        return new TagDto(tag.getId(), tag.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
