package com.funeat.review.domain;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Review {

    private static final double RANKING_GRAVITY = 0.5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private Long rating;

    private String content;

    private Boolean reBuy;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "review")
    private List<ReviewTag> reviewTags = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    private List<ReviewFavorite> reviewFavorites = new ArrayList<>();

    private Long favoriteCount = 0L;

    protected Review() {
    }

    public Review(final Member member, final Product findProduct, final Long rating, final String content,
                  final Boolean reBuy) {
        this(member, findProduct, null, rating, content, reBuy);
    }

    public Review(final Member member, final Product findProduct, final String image, final Long rating,
                  final String content, final Boolean reBuy) {
        this.member = member;
        this.product = findProduct;
        this.image = image;
        this.rating = rating;
        this.content = content;
        this.reBuy = reBuy;
    }

    public Review(final Member member, final Product findProduct, final String image, final Long rating,
                  final String content, final Boolean reBuy, final Long favoriteCount) {
        this.member = member;
        this.product = findProduct;
        this.image = image;
        this.rating = rating;
        this.content = content;
        this.reBuy = reBuy;
        this.favoriteCount = favoriteCount;
    }

    public Review(final Member member, final Product findProduct, final String image, final Long rating,
                  final String content, final Boolean reBuy, final Long favoriteCount, final LocalDateTime createdAt) {
        this.member = member;
        this.product = findProduct;
        this.image = image;
        this.rating = rating;
        this.content = content;
        this.reBuy = reBuy;
        this.favoriteCount = favoriteCount;
        this.createdAt = createdAt;
    }

    public void addFavoriteCount() {
        this.favoriteCount++;
    }

    public void minusFavoriteCount() {
        this.favoriteCount--;
    }

    public Double calculateRankingScore() {
        final long age = ChronoUnit.DAYS.between(createdAt, LocalDateTime.now());
        final double denominator = Math.pow(age + 1.0, RANKING_GRAVITY);
        return favoriteCount / denominator;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public Long getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public Boolean getReBuy() {
        return reBuy;
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }

    public List<ReviewFavorite> getReviewFavorites() {
        return reviewFavorites;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public List<ReviewTag> getReviewTags() {
        return reviewTags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
