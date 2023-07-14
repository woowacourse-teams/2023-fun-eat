package com.funeat.product.domain;

import com.funeat.member.domain.bookmark.ProductBookmark;
import java.util.List;
import javax.persistence.Entity;
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

    private Double averageRating;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductRecipe> productRecipes;

    @OneToMany(mappedBy = "product")
    private List<ProductBookmark> productBookmarks;

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
}
