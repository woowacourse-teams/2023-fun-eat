package com.funeat.tag.domain;

import com.funeat.review.domain.ReviewTag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected Tag() {
    }

    public Tag(final String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "tag")
    private List<ReviewTag> reviewTags = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<ReviewTag> getReviewTags() {
        return reviewTags;
    }
}
