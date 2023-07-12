package com.funeat.product.domain;

import com.funeat.category.domain.Category;
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
}
