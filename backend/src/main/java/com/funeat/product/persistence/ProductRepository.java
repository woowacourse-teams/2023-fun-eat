package com.funeat.product.persistence;

import com.funeat.common.repository.BaseRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductReviewCountDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends BaseRepository<Product, Long> {

    @Query("SELECT new com.funeat.product.dto.ProductReviewCountDto(p, COUNT(r.id)) "
            + "FROM Product p "
            + "LEFT JOIN Review r ON r.product.id = p.id "
            + "WHERE p.averageRating > 3.0 "
            + "AND r.createdAt BETWEEN :startDateTime AND :endDateTime "
            + "GROUP BY p.id")
    List<ProductReviewCountDto> findAllByAverageRatingGreaterThan3(final LocalDateTime startDateTime,
                                                                   final LocalDateTime endDateTime);

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
