package com.funeat.review.domain;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.product.domain.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
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

    private Double rating;

    private String content;

    private Boolean reBuy;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "review")
    private List<ReviewTag> reviewTags = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    private List<ReviewFavorite> reviewFavorites = new ArrayList<>();

    private Long favoriteCount = 0L;

    protected Review() {
    }

    public Review(final Member member, final Product findProduct, final String image, final Double rating,
                  final String content, final Boolean reBuy) {
        this.member = member;
        this.product = findProduct;
        this.image = image;
        this.rating = rating;
        this.content = content;
        this.reBuy = reBuy;
    }

    public Review(final Member member, final Product findProduct, final String image, final Double rating,
                  final String content, final Boolean reBuy, final Long favoriteCount) {
        this.member = member;
        this.product = findProduct;
        this.image = image;
        this.rating = rating;
        this.content = content;
        this.reBuy = reBuy;
        this.favoriteCount = favoriteCount;
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

    public Double getRating() {
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
}
