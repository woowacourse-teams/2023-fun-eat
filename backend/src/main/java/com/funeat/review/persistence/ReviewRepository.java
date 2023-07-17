package com.funeat.review.persistence;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Category, Long> {

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product = :product")
    Long countByProduct(@Param("product") Product product);
}
