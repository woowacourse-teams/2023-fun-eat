package com.funeat.product.domain;

import com.funeat.review.domain.Review;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private String image;

    private String content;

    private Double averageRating = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<ProductRecipe> productRecipes;

    private Long reviewCount = 0L;

    protected Product() {
    }

    public Product(final String name, final Long price, final String image, final String content,
                   final Category category) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.content = content;
        this.category = category;
    }

    public Product(final String name, final Long price, final String image, final String content,
                   final Double averageRating, final Category category) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.content = content;
        this.averageRating = averageRating;
        this.category = category;
    }

    public static Product create(final String name, final Long price, final String content, final Category category) {
        return new Product(name, price, null, content, category);
    }

    public void updateAverageRating(final Long rating, final Long count) {
        final double calculatedRating = ((count - 1) * averageRating + rating) / count;
        this.averageRating = Math.round(calculatedRating * 10.0) / 10.0;
    }

    public Double calculateRankingScore(final Long reviewCount) {
        final double exponent = -Math.log10(reviewCount + 1);
        final double factor = Math.pow(2, exponent);
        return averageRating - (averageRating - 3.0) * factor;
    }

    public void updateImage(final String topFavoriteImage) {
        this.image = topFavoriteImage;
    }

    public void update(final String name, final String content, final Long price, final Category category) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Category getCategory() {
        return category;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public void addReviewCount() {
        reviewCount++;
    }
}
