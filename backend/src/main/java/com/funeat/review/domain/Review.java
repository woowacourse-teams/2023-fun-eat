package com.funeat.review.domain;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Product;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Review {

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
