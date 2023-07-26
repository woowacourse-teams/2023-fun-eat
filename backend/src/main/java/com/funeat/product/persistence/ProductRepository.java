package com.funeat.product.persistence;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, COUNT(r)) " +
            "FROM Product p " +
            "LEFT JOIN p.reviews r " +
            "WHERE p.category = :category " +
            "GROUP BY p ")
    Page<ProductInCategoryDto> findAllByCategory(final @Param("category") Category category, final Pageable pageable);

    @Query("SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, COUNT(r)) " +
            "FROM Product p " +
            "LEFT JOIN p.reviews r " +
            "WHERE p.category = :category " +
            "GROUP BY p " +
            "ORDER BY COUNT(r) DESC, p.id ASC ")
    Page<ProductInCategoryDto> findAllByCategoryOrderByReviewCountDesc(final @Param("category") Category category, final Pageable pageable);
}
