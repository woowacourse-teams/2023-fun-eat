package com.funeat.product.persistence;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductReviewCountDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, COUNT(r)) "
                    + "FROM Product p "
                    + "LEFT JOIN p.reviews r "
                    + "WHERE p.category = :category "
                    + "GROUP BY p ",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.category = :category")
    Page<ProductInCategoryDto> findAllByCategory(@Param("category") final Category category, final Pageable pageable);

    @Query(value = "SELECT new com.funeat.product.dto.ProductInCategoryDto(p.id, p.name, p.price, p.image, p.averageRating, COUNT(r)) "
                    + "FROM Product p "
                    + "LEFT JOIN p.reviews r "
                    + "WHERE p.category = :category "
                    + "GROUP BY p "
                    + "ORDER BY COUNT(r) DESC, p.id DESC ",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.category = :category")
    Page<ProductInCategoryDto> findAllByCategoryOrderByReviewCountDesc(@Param("category") final Category category,
                                                                       final Pageable pageable);

    @Query("SELECT new com.funeat.product.dto.ProductReviewCountDto(p, COUNT(r.id)) "
            + "FROM Product p "
            + "LEFT JOIN Review r ON r.product.id = p.id "
            + "WHERE p.averageRating > 3.0 "
            + "GROUP BY p.id")
    List<ProductReviewCountDto> findAllByAverageRatingGreaterThan3();

    @Query("SELECT p FROM Product p "
            + "WHERE p.name LIKE CONCAT('%', :name, '%') "
            + "ORDER BY "
            + "(CASE WHEN p.name LIKE CONCAT(:name, '%') THEN 1 ELSE 2 END), "
            + "p.id DESC")
    List<Product> findAllByNameContainingFirst(@Param("name") final String name, final Pageable pageable);

    @Query("SELECT p FROM Product p "
            + "JOIN Product last ON last.id = :lastId "
            + "WHERE p.name LIKE CONCAT('%', :name, '%') "
            + "AND (last.name LIKE CONCAT(:name, '%') "
            + "AND ((p.name LIKE CONCAT(:name, '%') AND p.id < :lastId) OR (p.name NOT LIKE CONCAT(:name, '%'))) "
            + "OR (p.name NOT LIKE CONCAT(:name, '%') AND p.id < :lastId)) "
            + "ORDER BY (CASE WHEN p.name LIKE CONCAT(:name, '%') THEN 1 ELSE 2 END), p.id DESC")
    List<Product> findAllByNameContaining(@Param("name") final String name, final Long lastId, final Pageable pageable);

    @Query("SELECT new com.funeat.product.dto.ProductReviewCountDto(p, COUNT(r.id)) FROM Product p "
            + "LEFT JOIN Review r ON r.product.id = p.id "
            + "WHERE p.name LIKE CONCAT('%', :name, '%') "
            + "GROUP BY p.id "
            + "ORDER BY "
            + "(CASE WHEN p.name LIKE CONCAT(:name, '%') THEN 1 ELSE 2 END), p.id DESC")
    Page<ProductReviewCountDto> findAllWithReviewCountByNameContaining(@Param("name") final String name,
                                                                       final Pageable pageable);
}
