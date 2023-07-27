package com.funeat.tag.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TagType tagType;

    protected Tag() {
    }

    public Tag(final String name, final TagType tagType) {
        this.name = name;
        this.tagType = tagType;
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
