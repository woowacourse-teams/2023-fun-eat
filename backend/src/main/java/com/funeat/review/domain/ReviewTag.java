package com.funeat.review.domain;

import com.funeat.tag.domain.Tag;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    protected ReviewTag() {
    }

    private ReviewTag(final Review review, final Tag tag) {
        this.review = review;
        this.tag = tag;
    }

    public static ReviewTag createReviewTag(final Review review, final Tag tag) {
        final ReviewTag reviewTag = new ReviewTag(review, tag);
        review.getReviewTags().add(reviewTag);
        return reviewTag;
    }

    public Long getId() {
        return id;
    }

    public Review getReview() {
        return review;
    }

    public Tag getTag() {
        return tag;
    }
}
