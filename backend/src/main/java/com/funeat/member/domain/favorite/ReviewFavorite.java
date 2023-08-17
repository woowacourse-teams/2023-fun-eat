package com.funeat.member.domain.favorite;

import com.funeat.member.domain.Member;
import com.funeat.review.domain.Review;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "review_id"}))
public class ReviewFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    private Boolean favorite;

    protected ReviewFavorite() {
    }

    public ReviewFavorite(final Member member, final Review review) {
        this.member = member;
        this.review = review;
    }

    public static ReviewFavorite createReviewFavoriteByMemberAndReview(final Member member, final Review review,
                                                                       final Boolean favorite) {
        final ReviewFavorite reviewFavorite = new ReviewFavorite(member, review);
        reviewFavorite.review.getReviewFavorites().add(reviewFavorite);
        reviewFavorite.member.getReviewFavorites().add(reviewFavorite);
        reviewFavorite.favorite = favorite;
        reviewFavorite.review.addFavoriteCount();
        return reviewFavorite;
    }

    public void updateChecked(final Boolean favorite) {
        if (!this.favorite && favorite) {
            this.review.addFavoriteCount();
            this.favorite = favorite;
            return;
        }
        if (this.favorite && !favorite) {
            this.review.minusFavoriteCount();
            this.favorite = favorite;
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Review getReview() {
        return review;
    }

    public Boolean getFavorite() {
        return favorite;
    }
}
