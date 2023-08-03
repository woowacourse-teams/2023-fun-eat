package com.funeat.member.domain.favorite;

import com.funeat.member.domain.Member;
import com.funeat.review.domain.Review;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
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
        return reviewFavorite;
    }

    public void updateChecked(final Boolean favorite) {
        this.favorite = favorite;
        if (favorite) {
            this.review.addFavoriteCount();
            return;
        }
        this.review.minusFavoriteCount();

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
